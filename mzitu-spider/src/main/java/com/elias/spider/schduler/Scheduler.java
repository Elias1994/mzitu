package com.elias.spider.schduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.elias.common.entity.ImageInfo;
import com.elias.common.entity.SpiderLog;
import com.elias.common.repository.ImageInfoRepository;
import com.elias.common.repository.SpiderLogRepository;
import com.elias.spider.spider.HomePageableSpider;
import com.elias.spider.spider.ImageGroupSpider;
import com.elias.spider.spider.IncrementSpider;

@Component
public class Scheduler implements CommandLineRunner {
	@Autowired
	private ImageInfoRepository iiRep;
	@Autowired
	private SpiderLogRepository slRep;
	@Value("${spider.init}")
	private boolean isInit = false;
	@Value("${spider.increment}")
	private boolean isIncrement = false;

	private String base_pageurl = "https://www.mzitu.com/page/";
	private Logger logger = LoggerFactory.getLogger(Scheduler.class);

	@Override
	public void run(String... args) throws Exception {
		if (!isInit) {
			return;
		}
		logger.info("初始化数据爬虫开始。。。。");
		for (int i = 1; i <= 237; i++) {
			List<String> groupUrls = HomePageableSpider.getMainImageGroup(base_pageurl + i);
			for (String groupUrl : groupUrls) {
				// 根据图组地址获取图组index
				String indexStr = groupUrl.substring(groupUrl.lastIndexOf("/") + 1);
				Integer index = Integer.valueOf(indexStr);

				List<ImageInfo> infos = ImageGroupSpider.getImageUrls(index);
				iiRep.insert(infos);
			}
		}
		logger.info("初始化数据爬虫结束。。。。");
	}

	@Scheduled(fixedDelay = 30 * 60 * 1000)
	public void doScheduler() throws ParseException {
		if (!isIncrement) {
			return;
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(format.format(new Date()));
		List<SpiderLog> list = slRep.findByDateGreaterThan(date);
		if (list.size() > 4) {
			return;
		}
		logger.info("增量数据爬虫开始。。。。");
		List<String> groupUrls = IncrementSpider.getImageGroupUrls();
		if (groupUrls != null && groupUrls.size() > 0) {
			for (String groupUrl : groupUrls) {
				// 根据图组地址获取图组index
				String indexStr = groupUrl.substring(groupUrl.lastIndexOf("/") + 1);
				Integer index = Integer.valueOf(indexStr);

				List<ImageInfo> infos = ImageGroupSpider.getImageUrls(index);
				iiRep.saveAll(infos);
			}
			SpiderLog log = new SpiderLog(null, new Date(), groupUrls.size());
			slRep.save(log);
		}
		logger.info("增量数据爬虫开始。。。。");
	}

}

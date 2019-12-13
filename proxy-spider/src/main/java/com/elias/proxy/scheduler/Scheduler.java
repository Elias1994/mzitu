package com.elias.proxy.scheduler;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.elias.proxy.entity.ProxyInfo;
import com.elias.proxy.service.ProxyInfoService;
import com.elias.proxy.spider.SpiderProxy;
import com.elias.proxy.util.TestProxy;
import com.elias.proxy.util.ToolsUtil;

@Component
public class Scheduler {
	@Autowired
	private ProxyInfoService piSer;
	@Value("${proxy.spider}")
	private boolean isSpider = false;
	@Value("${proxy.test}")
	private boolean isTest = false;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static ExecutorService executorService = Executors.newFixedThreadPool(30);

	@Scheduled(fixedDelay = 1000 * 60 * 60 * 6)
	public void spiderProxy() {
		if (!isSpider) {
			return;
		}

		logger.info("spiderProxy begin");
		try {
			List<ProxyInfo> list = SpiderProxy.doSpider("http://www.xiladaili.com/gaoni/");
			piSer.insert(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("spiderProxy end");
	}

	@Scheduled(fixedDelay = 1000 * 60 * 5)
	public void testProxy() {
		if (!isTest) {
			return;
		}

		logger.info("testProxy begin");
		List<ProxyInfo> list = piSer.findAllNeedTest();
		logger.info("test ProxyInfo size:{}", list.size());
		for (ProxyInfo info : list) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					long ping = TestProxy.testProxy(info);
					info.setPing(ping);
					info.setNextTime(DateUtils.addMinutes(new Date(), ToolsUtil.getRandom(360, 720)));
					piSer.update(info);
				}
			});
		}
		logger.info("testProxy end");
	}
}

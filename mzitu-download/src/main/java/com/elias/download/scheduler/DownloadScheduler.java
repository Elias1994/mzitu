package com.elias.download.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.elias.common.entity.ImageInfo;
import com.elias.common.repository.ImageInfoRepository;
import com.elias.download.entity.DownloadCache;
import com.elias.download.entity.DownloadFailedLog;
import com.elias.download.repository.DownloadCacheRepository;
import com.elias.download.repository.DownloadFailedLogRepository;
import com.elias.download.spider.DownloadSpider;

@Component
public class DownloadScheduler {
	private Logger logger = LoggerFactory.getLogger(DownloadScheduler.class);
	@Value("${pageSize}")
	private int page_size = 100;
	@Value("${key}")
	private String key;
	@Value("${secret}")
	private String secret;
	@Value("${savePath}")
	private String savePath;
	@Autowired
	private ImageInfoRepository iiRep;
	@Autowired
	private DownloadCacheRepository dlcRep;
	@Autowired
	private DownloadFailedLogRepository dflRep;

//	@Autowired
//	private DownloadFailedLogHistoryRepository dflhRep;

	@Scheduled(fixedDelay = 20 * 1000)
	public void doScheduler() {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(secret)) {
			logger.error("KEY AND SECRET MUST NOT NULL !");
			return;
		}
		Optional<DownloadCache> optional = dlcRep.findById(key);
		DownloadCache cache = new DownloadCache();
		if (!optional.isPresent()) {
			logger.warn("NEW CACHE !");
			cache.setSecret(secret);
			cache.setKey(key);
			cache.setCurrentId("");
		} else if (!optional.get().getSecret().equals(secret)) {
			logger.error("ERROR SECRET !");
			return;
		} else {
			logger.info("CACHE INFO : {}", optional.get());
			cache = optional.get();
			cache.setUpdateTime(new Date().getTime());
		}

		if (!StringUtils.isEmpty(savePath)) {
			cache.setSavePath(savePath);
		}

		int index = 0;
		Sort sort = Sort.by(Direction.ASC, "id");
		Pageable page = PageRequest.of(index, 100, sort);
		String id = cache.getCurrentId();
		List<ImageInfo> list = null;
		if (!StringUtils.isEmpty(id)) {
			list = iiRep.findByIdGreaterThan(id, page).getContent();
		} else {
			list = iiRep.findAll(page).getContent();
		}
		while (list != null && list.size() > 0) {
			for (ImageInfo info : list) {
				try {
					DownloadSpider.downloadImage(info, cache.getSavePath());
					cache.setCurrentId(info.getId());
				} catch (Exception e) {
					DownloadFailedLog log = new DownloadFailedLog();
					log.setFailMsg(e.getMessage());
					log.setInfo(info);
					log.setSecret(secret);
					log.setKey(key);
					log.setSavePath(savePath);
					dflRep.save(log);
				}
			}
			dlcRep.save(cache);
			index++;
			page = PageRequest.of(index, 100, sort);
			if (!StringUtils.isEmpty(id)) {
				list = iiRep.findByIdGreaterThan(id, page).getContent();
			} else {
				list = iiRep.findAll(page).getContent();
			}
		}
	}
}

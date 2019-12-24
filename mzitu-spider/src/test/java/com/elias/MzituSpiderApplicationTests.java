package com.elias;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.elias.common.entity.ImageInfo;
import com.elias.common.repository.ImageInfoRepository;
import com.elias.spider.spider.DownloadSpider;

@SpringBootTest(classes = MzituSpiderApplication.class)
class MzituSpiderApplicationTests {

	@Autowired
	private ImageInfoRepository rep;
	@Autowired
	private DownloadSpider spider;
//	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	@Test
	void contextLoads() {
		int index = 0;
		Page<ImageInfo> list = null;
		do {
			Sort sort = Sort.by(Direction.ASC, "index");
			Pageable page = PageRequest.of(index, 100, sort);// 59892
//			list = rep.findByPublishTimeBefore(new Date(1519833600000L), page);
			list = rep.findByPublishTimeBeforeAndIndexGreaterThan(new Date(1519833600000L), 79231, page);
//			list = rep.findByIndexGreaterThan(0, page);
			for (ImageInfo imageInfo : list.getContent()) {
//				executorService.execute(new Runnable() {
//					@Override
//					public void run() {
				long begin = System.currentTimeMillis();
				spider.downloadImage(imageInfo);
				long end = System.currentTimeMillis();
				System.out.println("下载使用时间：" + (end - begin) + "ms");
//					}
//				});
			}
			index++;
		} while (list.hasNext());
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}

}

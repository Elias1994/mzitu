package com.elias.proxy.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elias.proxy.entity.ProxyInfo;

public class TestProxy {
	private static String testUrl = "http://icanhazip.com/";
	private static Logger logger = LoggerFactory.getLogger(TestProxy.class);

	public static long testProxy(ProxyInfo info) {
		logger.info("testProxy:{}", info);
		try {
			long start = System.currentTimeMillis();
			Document doc = Jsoup.connect(testUrl).proxy(info.getIp(), info.getPort()).timeout(5000).get();
			long end = System.currentTimeMillis();
			if (doc != null && doc.body().text() != null)
				return end - start;
		} catch (IOException e) {
		}
		return -1L;
	}
}

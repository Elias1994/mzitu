package com.elias.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupUtil {
	private static int max_error_times = 5;
	private static Logger log = LoggerFactory.getLogger(JsoupUtil.class);

	public static Document getDoc(String url) {
		log.info("获取页面信息开始：{}", url);
		int errorTimes = 0;
		Document doc = null;
		do {
			try {
//				ProxyResponse proxy = ProxyUtil.getProxy();
				doc = Jsoup.connect(url)
							.timeout(15000)
							.userAgent(ToolsUtil.getUserAgent())
//							.proxy(proxy.ip,proxy.port)
							.get();
				log.info("获取页面信息结束：{}", url);
				// 解析页面获取图片地址
				return doc;
			} catch (Exception e) {
				errorTimes++;
				doc = null;
				log.error("获取页面信息失败次数：{}", errorTimes, e);
				if (errorTimes < max_error_times) {
					log.info("5秒后重试。。。");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException ie) {
						log.error(ie.getMessage());
					}
					log.info("第{}次重试开始：{}", errorTimes + 1, url);
				} else {
					log.error("获取页面信息失败：{}", url);
					return null;
				}
			}
		} while (true);
	}

	// 获取最大页码
	public static int getMaxPage(Document doc) {
		// 解析页面，根据分页页码获取最大页码
		Elements eles = doc.getElementsByClass("pagenavi").get(0).children();
		Element ele = eles.get(eles.size() - 2);
		String text = ele.text();
		return Integer.valueOf(text);
	}

}

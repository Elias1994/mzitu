package com.elias.spider.spider;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elias.common.util.JsoupUtil;

/**
 * 根据分页地址获取图组地址
 * 
 * @author Elias
 * @date 2019年11月28日
 */
public class HomePageableSpider {
	private static Logger log = LoggerFactory.getLogger(HomePageableSpider.class);

	// 获取指定页的图组地址
	public static List<String> getMainImageGroup(String pageUrl) {
		log.info("getMainImageGroup 开始爬取，pageUrl:{}", pageUrl);
		ArrayList<String> list = new ArrayList<String>();
		Document doc = JsoupUtil.getDoc(pageUrl);
		// 解析返回的页面获取图组第一页，只获取主要的列表，页面其他位置的推荐图组不获取
		Element ele = doc.getElementById("pins");
		Elements lis = ele.children();
		for (Element li : lis) {
			Element a = li.child(0);
			String url = a.attr("href");
			list.add(url);
		}
		log.info("getMainImageGroup 爬取结束，结果数：{}\tpageUrl:{}", list.size(), pageUrl);
		return list;
	}

}

package com.elias.spider.spider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elias.common.util.JsoupUtil;

public class IncrementSpider {
	private static String url = "https://www.mzitu.com/all/";
	private static Logger log = LoggerFactory.getLogger(HomePageableSpider.class);

	public static List<String> getImageGroupUrls() {
		log.info("IncrementSpider 开始爬取，Url:{}", url);
		ArrayList<String> list = new ArrayList<String>();
		Document doc = JsoupUtil.getDoc(url);
		// 解析返回的页面获取图组第一页，只获取主要的列表，页面其他位置的推荐图组不获取
		String day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "日: ";
		List<Node> nodes = doc.getElementsByClass("url").get(0).childNodes();
		for (Node node : nodes) {
			if (day.equals(node.toString())) {
				list.add(nodes.get(node.siblingIndex() + 1).attr("href"));
			}
		}
		log.info("IncrementSpider 爬取结束，结果数：{}\tUrl:{}", list.size(), url);
		return list;
	}
}

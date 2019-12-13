package com.elias.proxy.spider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.elias.proxy.entity.ProxyInfo;
import com.elias.proxy.util.ToolsUtil;

public class SpiderProxy {

	public static List<ProxyInfo> doSpider(String url) throws Exception {
		Document doc = Jsoup.connect(url).timeout(5000).userAgent(ToolsUtil.getUserAgent()).get();
		Elements eles = doc.getElementsByClass("fl-table").get(0).child(1).children();
		List<ProxyInfo> list = new ArrayList<>();
		for (Element ele : eles) {
			String ipPort = ele.child(0).text();
			String[] split = ipPort.split(":");
			String ip = split[0];
			int port = Integer.valueOf(split[1]);
			String location = ele.child(3).text();
			ProxyInfo info = new ProxyInfo(ip, port, location, new Date(), new Date(), 0L);
			list.add(info);
		}
		return list;
	}

}

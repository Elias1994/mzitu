package com.elias.spider.spider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.elias.common.entity.ImageInfo;
import com.elias.common.util.JsoupUtil;

/**
 * 指定index，获取图组中所有图片信息
 * 
 * @author Elias
 * @date 2019年11月28日
 */
public class ImageGroupSpider {
	private static Logger log = LoggerFactory.getLogger(HomePageableSpider.class);
	private static String base_url = "https://www.mzitu.com/";

	public static List<ImageInfo> getImageUrls(Integer index) {
		List<ImageInfo> list = new ArrayList<ImageInfo>();

		// 根据图组index,拼接图组地址获取页面信息
		String homeUrl = base_url.concat(index.toString());
		Document doc = JsoupUtil.getDoc(homeUrl);

		// 获取组图的标题和类别
		String desc = doc.title().replace(" - 妹子图", "");
		String[] split = desc.split(" - ");
		String title = split[0];
		String group = split[1];

		// 组图的最大页码也就是图片张数
		int maxPage = JsoupUtil.getMaxPage(doc);
		// 图组的发布时间
		Date publishTime = null;
		try {
			publishTime = getPuclishTime(doc);
		} catch (ParseException e1) {
			log.error("获取发布日期失败：{}", homeUrl);
		}

		// 创建图片信息存入集合
		for (Integer page = 1; page <= maxPage; page++) {
			String pageUrl = homeUrl.concat("/").concat(page.toString());
			String url = JsoupUtil.getDoc(pageUrl).getElementsByAttributeValue("alt", title).attr("src");

			try {
				if (StringUtils.isEmpty(url)) {
					throw new NullPointerException("图片url不能为空");
				}
				ImageInfo imageInfo = new ImageInfo(null, url, index, group, title, page, publishTime);
				log.info("创建图片信息成功：{}", imageInfo);
				list.add(imageInfo);
			} catch (Exception e) {
				log.error("创建图片信息失败！", e);
			}

		}
		return list;
	}

	// 获取图片发布时间
	private static Date getPuclishTime(Document doc) throws ParseException {
		String dateStr = doc.getElementsByClass("main-meta").get(0).child(1).text().replace("发布于 ", "");
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.parse(dateStr);
	}

}

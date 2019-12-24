package com.elias.download.spider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.elias.common.entity.ImageInfo;
import com.elias.common.util.FileUtil;

/**
 * 根据图片信息下载图片
 * 
 * @author Elias
 * @date 2019年11月28日
 */
@Component
public class DownloadSpider {
	private static Logger log = LoggerFactory.getLogger(DownloadSpider.class);
	private static String base_url = "https://www.mzitu.com/";
	private static int max_error_times = 5;

	// 下载图片，保存路径为“配置的基本路径/分类/index_标题/页码.jpg”
	public static void downloadImage(ImageInfo info, String savePath) throws Exception {
		log.info("下载图片开始：{}", info);
		if (info == null) {
			log.error("ImageInfo is null");
			return;
		}
		// 获取图片所属页面作为Referer
		String referer = base_url.concat(info.getIndex().toString());

		// 获取图片格式后缀
		String imageSuffix = info.getUrl().substring(info.getUrl().lastIndexOf("."));

		// 图片保存文件夹路径
		String imageSavePath = savePath.concat(info.getGroup().concat("/")).concat(info.getIndex() + "-")
				.concat(info.getTitle());
		// 创建文件夹
		try {
			FileUtil.initSavePath(imageSavePath);
		} catch (Exception e1) {
			log.error("downloadImage 初始化文件夹失败：{} ", e1.getMessage());
			return;
		}

		// 图片路径
		String imagePath = imageSavePath.concat("/").concat(info.getPage().toString()).concat(imageSuffix);
		String allImagePath = savePath.concat(info.getGroup().concat("/")).concat("all/").concat(info.getIndex() + "-")
				.concat(info.getTitle()).concat("-").concat(info.getPage().toString()).concat(imageSuffix);

		// 出错次数
		int errorTimes = 0;
		do {
			try {
				FileUtil.download(info.getUrl(), imagePath, base_url);
				copyFile(new File(imagePath), new File(allImagePath));
				log.info("下载图片结束：{}", info);
//				Thread.sleep(200);
				return;
			} catch (Exception e) {
				errorTimes++;
				if (errorTimes >= max_error_times) {
					log.error("图片下载失败：{}", referer);
					throw e;
				}
				log.error("下载图片失败1秒后重试");
				try {
					// 高频率访问网站可能导致被反爬，暂停5秒
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					log.error(ie.getMessage());
				}
			}
		} while (errorTimes < max_error_times);
	}

	private static void copyFile(File form, File to) {
		long startTime = System.currentTimeMillis();
		InputStream is = null;
		OutputStream os = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			is = new FileInputStream(form);
			os = new FileOutputStream(to);
			// 创建缓冲流
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(os);
			byte[] buffer = new byte[819200];
			int count = bis.read(buffer);
			while (count != -1) {
				// 使用缓冲流写数据
				bos.write(buffer, 0, count);
				// 刷新
				bos.flush();
				count = bis.read(buffer);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println("复制完成,耗时:" + (endTime - startTime) + "毫秒");
	}
}

package com.elias.common.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.tomcat.util.codec.binary.Base64;

public class ImageUtil {
	public static String imgBase64(String imgURL) {
		ByteArrayOutputStream outPut = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		try {
			// 创建URL
			URL url = new URL(imgURL);
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10 * 1000);
			conn.setRequestProperty("User-Agent", ToolsUtil.getUserAgent());
			conn.setRequestProperty("Referer", "mzitu.com");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return "fail";// 连接失败/链接失效/图片不存在
			}
			InputStream inStream = conn.getInputStream();
			int len = -1;
			while ((len = inStream.read(data)) != -1) {
				outPut.write(data, 0, len);
			}	
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		return Base64.encodeBase64String(outPut.toByteArray());
	}

	public static String GetImageStr(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		return Base64.encodeBase64String(data);// 返回Base64编码过的字节数组字符串
	}

	public static boolean GenerateImage(String base64str, String savepath) { // 对字节数组字符串进行Base64解码并生成图片
		if (base64str == null) // 图像数据为空
			return false;
		// System.out.println("开始解码");
		try {
			// Base64解码
			byte[] b = Base64.decodeBase64(base64str);
			// System.out.println("解码完成");
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// System.out.println("开始生成图片");
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(savepath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {
		String base64 = imgBase64("http://file02.16sucai.com/d/file/2014/0829/372edfeb74c3119b666237bd4af92be5.jpg");
		System.out.println(base64);
		GenerateImage(base64, "d:1.jpeg");
	}
}

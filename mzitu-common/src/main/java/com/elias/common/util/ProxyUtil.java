package com.elias.common.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import lombok.ToString;

public class ProxyUtil {

	private static Logger logger = LoggerFactory.getLogger(ProxyUtil.class);
//	private static String url = "http://proxy.mofangdata.cn/api/getip/get.htm";
//	private static String url = "http://localhost:9999/?auth=qscft";
//	private static String url = "http://59.110.159.173:9999/?auth=qscft";

	private static RestTemplate restTemplate;

	static {
		restTemplate = new RestTemplate();
	}

	public static ProxyResponse getProxyResponse() {
		try {
			ProxyResponse response = restTemplate.getForObject("http://localhost:9999/?auth=qscft",
					ProxyResponse.class);
			if (response == null || response.code != 1) {
				logger.warn("获取代理失败：{}", response);
				return null;
			}
			return response;
		} catch (Exception e) {
			logger.error("getProxy", e);
			return null;
		}
	}

	public static Proxy getProxy() {
		for (int i = 0; i < 20; i++) {
			logger.info("get proxy try time:{}", i + 1);
			try {
				String proxyUrl = Jsoup.connect(
						"http://svip.kdlapi.com/api/getproxy/?orderid=936273988936860&num=1&protocol=1&method=2&an_an=1&an_ha=1&quality=2&sep=1")
						.get().text();
				String[] Strs = proxyUrl.split(":");
				SocketAddress address = new InetSocketAddress(Strs[0], Integer.valueOf(Strs[1]));
				Proxy proxy = new Proxy(Proxy.Type.HTTP, address);

				Document doc = Jsoup.connect("http://icanhazip.com/").proxy(proxy).timeout(5000).get();
				logger.info("proxy:{}", proxy);
				// return proxy;
			} catch (Exception e) {
				logger.error(e.getMessage());
				continue;
			}
		}
		return null;
	}

	public static Proxy getHlztProxy() {
		try {
			ProxyResponse response = restTemplate.getForObject("http://proxy.mofangdata.cn/api/getip/get.htm",
					ProxyResponse.class);
//			logger.info("getProxy response:{}", response);
			if (response == null || response.ret != 1) {
				// logger.warn("获取代理失败：{}", response);
				return null;
			}
			SocketAddress address = new InetSocketAddress(response.ip, 8888);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
			return proxy;
		} catch (Exception e) {
			// logger.error("getProxy", e);
			return null;
		}
	}

	@ToString
	static class ProxyResponse {
		public int ret;
		public int code;
		public String msg;
		public String ip;
		public int port;
		public String location;
	}

	public static void main(String[] args) throws IOException {
		int count = 0;
		Response response = Jsoup.connect("https://i5.meizitu.net/2019/12/12a06.jpg").execute();
//				.response();
		byte[] data = response.bodyAsBytes();
//		for (int i = 0; i < 20; i++) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						Proxy proxy = getHlztProxy();
//						System.out.println(proxy);
//						// 创建URL
//						URL url = new URL("");
//						// 创建链接
//						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//						conn.setRequestMethod("GET	");
//						conn.setConnectTimeout(10 * 1000);
//						conn.S
//						conn.setRequestProperty("User-Agent", ToolsUtil.getUserAgent());
//						conn.setRequestProperty("Referer", "mzitu.com");
//
////						if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
////							return "fail";// 连接失败/链接失效/图片不存在
////						}
//						logger.info("ResponseCode:{}", conn.getResponseCode());
//					} catch (IOException e) {
//						// e.printStackTrace();
//					}
//				}
//			}).start();
//
//		}
		System.out.println(count);
	}
}

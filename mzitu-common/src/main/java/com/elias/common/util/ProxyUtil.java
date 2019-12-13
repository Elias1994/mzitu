package com.elias.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import lombok.ToString;

public class ProxyUtil {

	private static Logger logger = LoggerFactory.getLogger(ProxyUtil.class);

	private static String url = "http://localhost:9999/?auth=qscft";
//	private static String url = "http://59.110.159.173:9999/?auth=qscft";

	private static RestTemplate restTemplate;

	static {
		restTemplate = new RestTemplate();
	}

	public static ProxyResponse getProxy() {
		try {
			ProxyResponse response = restTemplate.getForObject(url, ProxyResponse.class);
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

	@ToString
	static class ProxyResponse {
		public int code;
		public String msg;
		public String ip;
		public int port;
		public String location;
	}
}

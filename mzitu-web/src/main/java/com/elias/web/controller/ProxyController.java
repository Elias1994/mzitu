package com.elias.web.controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elias.common.entity.ImageInfo;
import com.elias.common.util.ToolsUtil;
import com.elias.web.service.WebService;

@Controller
@RequestMapping
public class ProxyController {
	@Autowired
	private WebService webSer;

	@RequestMapping("getImage")
	public void getImage(@RequestParam(required = true, name = "id") String id, HttpServletResponse resp)
			throws Exception {
		ImageInfo info = null;
		info = webSer.findById(id);
		resp.setContentType("image/png");
		ServletOutputStream outPut = resp.getOutputStream();
		byte[] data = new byte[1024];
		// 创建URL
		URL url = new URL(info.getUrl());
		// 创建链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(10 * 1000);
		conn.setRequestProperty("User-Agent", ToolsUtil.getUserAgent());
		conn.setRequestProperty("Referer", "mzitu.com");

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			resp.setStatus(500);
		}
		InputStream inStream = conn.getInputStream();
		int len = -1;
		while ((len = inStream.read(data)) != -1) {
			outPut.write(data, 0, len);
		}
		inStream.close();
		outPut.flush();
		outPut.close();
	}
}

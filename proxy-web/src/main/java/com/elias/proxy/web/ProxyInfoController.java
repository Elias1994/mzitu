package com.elias.proxy.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elias.proxy.entity.ProxyInfo;
import com.elias.proxy.entity.ProxyResponse;
import com.elias.proxy.service.ProxyInfoService;

@RestController
@Scope()
public class ProxyInfoController {
	@Autowired
	private ProxyInfoService piSer;

	@RequestMapping
	public ProxyResponse getProxy(String auth) {
		if (!"qscft".equals(auth)) {
			return new ProxyResponse(-1, "UnAuthration");
		}
		ProxyInfo info = piSer.getRandomUsefulProxy();
		if (info != null && !StringUtils.isEmpty(info.getIp()) && info.getPort() != 0) {
			return new ProxyResponse(1, "success", info.getIp(), info.getPort(), info.getLocation());
		} else {
			return new ProxyResponse(-1, "Unkonw Error");
		}
	}

}

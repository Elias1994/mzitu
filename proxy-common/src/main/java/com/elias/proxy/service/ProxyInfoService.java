package com.elias.proxy.service;

import java.util.List;

import com.elias.proxy.entity.ProxyInfo;

public interface ProxyInfoService {

	public void insert(ProxyInfo info);

	public void insert(List<ProxyInfo> infos);

//	public List<ProxyInfo> findAll(Integer size, Integer page, Direction direction, String... orders);
//
//	public List<ProxyInfo> findAll(Integer size, Integer page, String... orders);
//
//	public List<ProxyInfo> findAll(Integer size, Integer page);
//
//	public List<ProxyInfo> findAll(Integer size, Direction direction, String... orders);
//
//	public List<ProxyInfo> findAll(Integer size, String... orders);

	public void update(ProxyInfo info);

	public List<ProxyInfo> findAllNeedTest();

	public ProxyInfo getRandomUsefulProxy();
}

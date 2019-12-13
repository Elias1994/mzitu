package com.elias.spider.service;

import java.util.List;

import com.elias.common.entity.ImageInfo;

public interface SpiderService {
	public void insertAll(List<ImageInfo> infos);

	public void insert(ImageInfo info);
}

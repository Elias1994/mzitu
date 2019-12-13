package com.elias.spider.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elias.common.entity.ImageInfo;
import com.elias.common.repository.ImageInfoRepository;

@Service
public class SpiderServiceImpl implements SpiderService {
	@Autowired
	private ImageInfoRepository iip;

	@Override
	public void insertAll(List<ImageInfo> infos) {
		for (ImageInfo imageInfo : infos) {
			insert(imageInfo);
		}

	}

	@Override
	public void insert(ImageInfo info) {
		Optional<ImageInfo> optional = iip.findByUrl(info.getUrl());
		if (!optional.isPresent()) {
			iip.save(info);
		}
	}

}

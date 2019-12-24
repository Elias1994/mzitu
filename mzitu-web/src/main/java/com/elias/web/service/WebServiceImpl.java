package com.elias.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.elias.common.entity.ImageInfo;
import com.elias.common.repository.ImageInfoRepository;

@Service
public class WebServiceImpl implements WebService {
	@Autowired
	private ImageInfoRepository iiRep;

	@Override
	public ImageInfo findById(String id) throws Exception {
		if (StringUtils.isEmpty(id)) {
			throw new Exception("id不能为空!");
		}
		Optional<ImageInfo> optional = iiRep.findById(id);
		if (optional.isPresent() && !StringUtils.isEmpty(optional.get().getUrl())) {
			return optional.get();
		} else if (!optional.isPresent()) {
			throw new Exception("无此图片数据!");
		} else {
			throw new Exception("图片数据无url!");
		}
	}

}

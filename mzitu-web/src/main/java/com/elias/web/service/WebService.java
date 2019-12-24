package com.elias.web.service;

import com.elias.common.entity.ImageInfo;

public interface WebService {

	ImageInfo findById(String id) throws Exception;

}

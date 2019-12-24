package com.elias.common.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.elias.common.entity.ImageInfo;

public interface ImageInfoRepository extends MongoRepository<ImageInfo, String> {

	Optional<ImageInfo> findByUrl(String url);

	Page<ImageInfo> findByPublishTimeBefore(Date date, Pageable page);

	Page<ImageInfo> findByIndexGreaterThan(int i, Pageable page);

	Page<ImageInfo> findByPublishTimeBeforeAndIndexGreaterThan(Date date, int i, Pageable page);

	Page<ImageInfo> findByIdGreaterThan(String id, Pageable page);

}

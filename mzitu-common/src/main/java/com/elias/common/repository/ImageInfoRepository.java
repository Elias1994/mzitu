package com.elias.common.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.elias.common.entity.ImageInfo;

public interface ImageInfoRepository extends MongoRepository<ImageInfo, String> {

	Optional<ImageInfo> findByUrl(String url);

}

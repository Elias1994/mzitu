package com.elias.download.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.elias.download.entity.DownloadCache;

public interface DownloadCacheRepository extends MongoRepository<DownloadCache, String> {

}

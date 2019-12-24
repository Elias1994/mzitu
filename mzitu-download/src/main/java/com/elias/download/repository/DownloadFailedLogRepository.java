package com.elias.download.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.elias.download.entity.DownloadFailedLog;

public interface DownloadFailedLogRepository extends MongoRepository<DownloadFailedLog, String> {

}

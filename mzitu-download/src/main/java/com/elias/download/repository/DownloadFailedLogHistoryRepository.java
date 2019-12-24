package com.elias.download.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.elias.download.entity.DownloadFailedLogHistory;

public interface DownloadFailedLogHistoryRepository extends MongoRepository<DownloadFailedLogHistory, String> {

}

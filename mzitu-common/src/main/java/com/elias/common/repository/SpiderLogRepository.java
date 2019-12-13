package com.elias.common.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.elias.common.entity.SpiderLog;

public interface SpiderLogRepository extends MongoRepository<SpiderLog, String> {

	List<SpiderLog> findByDateGreaterThan(Date date);

}

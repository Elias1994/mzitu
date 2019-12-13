package com.elias.proxy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.elias.proxy.entity.ProxyInfo;

public interface ProxyInfoRepository extends MongoRepository<ProxyInfo, String> {

	List<ProxyInfo> findByNextTimeLessThanOrNextTimeNull(Date date);

	Page<ProxyInfo> findByPingGreaterThan(int i, Pageable pageable);

//	List<ProxyInfo> findByType(String type);

}

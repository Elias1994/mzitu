package com.elias.proxy.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.elias.proxy.entity.ProxyInfo;
import com.elias.proxy.repository.ProxyInfoRepository;
import com.elias.proxy.util.ToolsUtil;

@Service
public class ProxyInfoServiceImpl implements ProxyInfoService {
	@Autowired
	private ProxyInfoRepository piRep;

	@Override
	public void insert(ProxyInfo info) {
		Optional<ProxyInfo> optional = piRep.findById(info.getIp());
		info.setTime(new Date());
		if (!optional.isPresent()) {
			piRep.insert(info);
		}
	}

	@Override
	public void insert(List<ProxyInfo> infos) {
		if (infos != null && infos.size() > 0)
			for (ProxyInfo info : infos) {
				insert(info);
			}

	}

//	@Override
//	public List<ProxyInfo> findAll(Integer size, Integer page, Direction direction, String... orders) {
//		Sort sort = null;
//		if (orders != null && orders.length > 0 && direction != null) {
//			sort = Sort.by(direction, orders);
//		} else if (orders != null && orders.length > 0) {
//			sort = Sort.by(orders);
//		}
//
//		Pageable pageable = null;
//		if (sort != null && page != null && size != null) {
//			pageable = PageRequest.of(page, size, sort);
//		} else if (page != null && size != null) {
//			pageable = PageRequest.of(page, size);
//		} else if (size != null) {
//			pageable = PageRequest.of(1, size);
//		}
//
//		List<ProxyInfo> list = null;
//		if (pageable != null) {
//			list = piRep.findAll(pageable).getContent();
//		} else if (pageable == null && sort != null) {
//			list = piRep.findAll(sort);
//		} else {
//			list = piRep.findAll();
//		}
//
//		return list;
//	}
//
//	@Override
//	public List<ProxyInfo> findAll(Integer size, Integer page, String... orders) {
//		return this.findAll(size, page, null, orders);
//	}
//
//	@Override
//	public List<ProxyInfo> findAll(Integer size, Integer page) {
//		return this.findAll(size, page, new String[0]);
//	}
//
//	@Override
//	public List<ProxyInfo> findAll(Integer size, Direction direction, String... orders) {
//		return this.findAll(size, 1, direction, orders);
//	}
//
//	@Override
//	public List<ProxyInfo> findAll(Integer size, String... orders) {
//		return this.findAll(size, 1, null, orders);
//	}

	@Override
	public void update(ProxyInfo info) {
		info.setTime(new Date());
		piRep.save(info);
	}

	@Override
	public List<ProxyInfo> findAllNeedTest() {
		return piRep.findByNextTimeLessThanOrNextTimeNull(new Date());
	}

	@Override
	public ProxyInfo getRandomUsefulProxy() {
		try {
			Pageable pageable = PageRequest.of(1, 100, Direction.DESC, "ping");
			Page<ProxyInfo> page = piRep.findByPingGreaterThan(0, pageable);
			if (page.hasContent()) {
				List<ProxyInfo> list = page.getContent();
				return list.get(ToolsUtil.getRandom(0, list.size()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

package com.daou.service;

import com.daou.entity.Account;
import com.daou.entity.BandLog;
import com.daou.repository.BandLogRepository;
import com.daou.types.BandLogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Song
 */

@Service
public class BandLogService {
	@Autowired
	private BandLogRepository bandLogRepository;

	public List<BandLog> findAll() {
		List<BandLog> bandLogs = new ArrayList<>();
		bandLogRepository.findAll().forEach(e -> bandLogs.add(e));
		return bandLogs;
	}

	public Optional<BandLog> findBySeqLogNo(Long seqLogNo) {
		Optional<BandLog> bandLogs = bandLogRepository.findBySeqLogNo(seqLogNo);
		return bandLogs;
	}
	
	public Optional<BandLog> findBySerialNo(String serialNo) {
		Optional<BandLog> bandLogs = bandLogRepository.findBySerialNo(serialNo);
		return bandLogs;
	}
	
	public List<BandLog> findByRevType(BandLogType revType) {
		List<BandLog> bandLogs = new ArrayList<>();
		bandLogRepository.findByRevType(revType).forEach(e -> bandLogs.add(e));
		return bandLogs;
	}
}

package com.daou.service;

import com.daou.entity.BandReceive;
import com.daou.mapping.bandMapping;
import com.daou.repository.BandReceiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Song
 */

@Service
public class BandReceiveService {
	@Autowired
	private BandReceiveRepository bandReceiveRepository;

	public List<BandReceive> findAll() {
		List<BandReceive> BandReceives = new ArrayList<>();
		bandReceiveRepository.findAll().forEach(e -> BandReceives.add(e));
		return BandReceives;
	}

	//카테고리 포함한 전체 수신번호 목록
	public List<bandMapping> findAllBy() {
		List<bandMapping> BandReceives = new ArrayList<>();
		bandReceiveRepository.findAllBy().forEach(e -> BandReceives.add(e));
		return BandReceives;
	}

	public Optional<BandReceive> findBySeqNo(Long seqNo) {
		Optional<BandReceive> BandReceive = bandReceiveRepository.findBySeqNo(seqNo);
		return BandReceive;
	}
	
	public List<BandReceive> findBySerialNo(String serialNo) {
		List<BandReceive> BandReceives = new ArrayList<>();
		bandReceiveRepository.findBySerialNo(serialNo).forEach(e -> BandReceives.add(e));;
		return BandReceives;
	}
	
	public List<BandReceive> findByReceiveNo(String receiveNo) {
		List<BandReceive> BandReceives = new ArrayList<>();
		bandReceiveRepository.findByReceiveNo(receiveNo).forEach(e -> BandReceives.add(e));
		return BandReceives;
	}

}

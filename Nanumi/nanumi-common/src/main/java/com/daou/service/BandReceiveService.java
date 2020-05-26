package com.daou.service;

import com.daou.entity.BandReceive;
import com.daou.repository.BandReceiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BandReceiveService {
	@Autowired
	private BandReceiveRepository bandReceiveRepository;

	public List<BandReceive> findAll() {
		List<BandReceive> BandReceives = new ArrayList<>();
		bandReceiveRepository.findAll().forEach(e -> BandReceives.add(e));
		return BandReceives;
	}

	public Optional<BandReceive> findBySeqNo(Long seqNo) {
		Optional<BandReceive> BandReceive = bandReceiveRepository.findBySeqNo(seqNo);
		return BandReceive;
	}
	
	public Optional<BandReceive> findBySerialNo(String serialNo) {
		Optional<BandReceive> BandReceive = bandReceiveRepository.findBySerialNo(serialNo);
		return BandReceive;
	}
	
	public List<BandReceive> findByReceiveNo(String receiveNo) {
		List<BandReceive> BandReceives = new ArrayList<>();
		bandReceiveRepository.findByReceiveNo(receiveNo).forEach(e -> BandReceives.add(e));
		return BandReceives;
	}

}

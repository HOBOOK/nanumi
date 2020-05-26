package com.daou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daou.entity.BandLog;
import com.daou.repository.BandLogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	public List<BandLog> findByRevType(byte revType) {
		List<BandLog> bandLogs = new ArrayList<>();
		bandLogRepository.findByRevType(revType).forEach(e -> bandLogs.add(e));
		return bandLogs;
	}
	
//
//	public Optional<Member> findById(String id) {
//		Optional<Member> member = memberRepository.findById(id);
//		return member;
//	}
//
//	public void deleteById(Long mbrNo) {
//		memberRepository.deleteById(mbrNo);
//	}
//
//	public void updateById(Long mbrNo, MemberVo member) {
//		Optional<MemberVo> e = memberRepository.findById(mbrNo);
//		if (e.isPresent()) {
//			e.get().setMbrNo(member.());
//			e.get().setId(member.getId());
//			e.get().setName(member.getName());
//			memberRepository.save(member);
//		}
//	}
}

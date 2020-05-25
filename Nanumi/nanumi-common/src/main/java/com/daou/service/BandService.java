package com.daou.service;
//package com.daou.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.daou.authentication.entity.BandVo;
//import com.daou.repository.BandRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class BandService {
//	@Autowired
//	private BandRepository bandRepository;
//
////	public List<BandVo> findAll() {
////		List<BandVo> bands = new ArrayList<>();
////		bandRepository.findAll().forEach(e -> bands.add(e));
////		return bands;
////	}
////
////	public Optional<BandVo> findById(Long mbrNo) {
////		Optional<BandVo> member = bandRepository.findById(mbrNo);
////		return member;
////	}
////
////	public void deleteById(Long mbrNo) {
////		bandRepository.deleteById(mbrNo);
////	}
//
//	public BandVo save(BandVo member) {
//		bandRepository.save(member);
//		return member;
//	}
//
////	public void updateById(Long mbrNo, BandVo member) {
////		Optional<BandVo> e = bandRepository.findById(mbrNo);
////		if (e.isPresent()) {
////			e.get().setMbrNo(member.getMbrNo());
////			e.get().setId(member.getId());
////			e.get().setName(member.getName());
////			bandRepository.save(member);
////		}
////	}
//}

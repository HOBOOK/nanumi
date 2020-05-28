package com.daou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daou.entity.Band;
import com.daou.repository.BandRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Song
 */
@Service
public class BandService {
	@Autowired
	private BandRepository bandRepository;

	public List<Band> findAll() {
		List<Band> bands = new ArrayList<>();
		bandRepository.findAll().forEach(e -> bands.add(e));
		return bands;
	}

	public Optional<Band> findBySeqNo(Long SeqNo) {
		Optional<Band> band = bandRepository.findBySeqNo(SeqNo);
		return band;
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
	public Band save(Band band) {
		bandRepository.save(band);
		return band;
	}

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

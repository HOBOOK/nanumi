package com.daou.service;

import com.daou.entity.BandAssign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daou.entity.Band;
import com.daou.repository.BandRepository;

import javax.transaction.Transactional;
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

	public Optional<Band> findBySerialNo(String SerialNo) {
		Optional<Band> band = bandRepository.findBySerialNo(SerialNo);
		return band;
	}

	public List<Band> findByCategory(com.daou.types.category category) {
		List<Band> categoryBands = new ArrayList<>();
		bandRepository.findByCategory(category).forEach(e -> categoryBands.add(e));
		return categoryBands;
	}

	@Transactional
	public Band save(Band band) {
		bandRepository.save(band);
		return band;
	}


//	public void deleteById(Long mbrNo) {
//		memberRepository.deleteById(mbrNo);
//	}
//

}

package com.daou.repository;


//import com.daou.Mapping.BandReceiveMapping;
import com.daou.entity.BandReceive;
import com.daou.mapping.bandMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Song
 */

@Repository 
public interface BandReceiveRepository extends JpaRepository<BandReceive, Long> {
	//비워있어도 잘 작동함. 
	// long 이 아니라 Long으로 작성. ex) int => Integer 같이 primitive형식 사용못함
	// findBy뒤에 컬럼명을 붙여주면 이를 이용한 검색이 가능하다

	public List<bandMapping> findAllBy(); // band Table과 조인후 category column만 추가 조회
	public Optional<BandReceive> findBySeqNo(Long seqLogNo);
	public Optional<BandReceive> findBySerialNo(String serialNo);
	public List<BandReceive> findByReceiveNo(String receiveNo);

}
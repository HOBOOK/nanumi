package com.daou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Song
 */

@Data
@Entity
@Table(name="band_tb")
public class Band {

	@Column(name = "seq_no", nullable = false, insertable = false)
	private Long seqNo;
	
	@Id
	@Column(name = "serial_no", nullable = false)
	private String serialNo;

	@Column(name = "country_no", nullable = false)
	private String countryNo;

	@Column(name = "local_no", nullable = false)
	private String localNo;

	@Column(name = "base_no", nullable = false)
	private String baseNo;

	@Column(name = "start_no", nullable = false)
	private String startNo;
	
	@Column(name = "end_no", nullable = false)
	private String endNo;
	
	@Column(name = "category", nullable = false)
	@Enumerated(EnumType.STRING)	//EnumType.STRING : enum의 이름을 DB에 저장, EnumType.ORDINAL : enum 순서 값을 DB에 저장
	private com.daou.types.category category;

	@Column(name = "status", nullable = false, insertable = false)
	private byte status;

	//OneToMany 추가 Assign_tb 내용 조회 -> 모달 출력


	@Builder
	public Band(Long seqNo, String serialNo, String countryNo, String localNo, String baseNo
			, String startNo, String endNo, com.daou.types.category category, byte status) {
	    //결합도 ? 동결 ?
	    //유효성 검사 - 객체에 필요한 값이 없는 경우에 객체 생성 진행 안됨 -> NOT NULL 제약조건 컬럼들 추가 예정
        Assert.hasText(String.valueOf(seqNo), "seqNo must not be empty");

	    this.seqNo = seqNo;
		this.serialNo = serialNo;
		this.countryNo = countryNo;
		this.localNo = localNo;
		this.baseNo = baseNo;
		this.startNo = startNo;
		this.endNo = endNo;
		this.category = category;
		this.status = status;
	}
	
	public Band() {}

//	public String toString() {
//		return String.format("seqNo=%d serialNo=%s category=%s, ", seqNo,serialNo,category);
//	}
}

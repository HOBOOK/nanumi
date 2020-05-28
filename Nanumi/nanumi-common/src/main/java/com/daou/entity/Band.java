package com.daou.entity;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;

/**
 * @author Song
 */

@Data
@Entity
@Table(name="band_tb")
public class Band {

	@Column(name = "seq_no")
	private Long seqNo;
	
	@Id
	@Column(name = "serial_no")
	private String serialNo;

	@Column(name = "country_no")
	private String countryNo;

	@Column(name = "local_no")
	private String localNo;

	@Column(name = "base_no")
	private String baseNo;

	@Column(name = "start_no")
	private String startNo;
	
	@Column(name = "end_no")
	private String endNo;
	
	@Column(name = "category")
	@Enumerated(EnumType.STRING)	//EnumType.STRING : enum의 이름을 DB에 저장, EnumType.ORDINAL : enum 순서 값을 DB에 저장
	private com.daou.types.category category;
	
	@Builder
	public Band(Long seqNo, String serialNo, String countryNo, String localNo, String baseNo
			, String startNo, String endNo, com.daou.types.category category) {
	    
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
	}
	
	public Band() {}

//	public String toString() {
//		return String.format("seqNo=%d serialNo=%s category=%s, ", seqNo,serialNo,category);
//	}
}

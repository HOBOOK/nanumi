package com.daou.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author Song
 */

@Data
@Entity
@Table(name="BAND_ASSIGN_TB")
public class BandAssign {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	//데이터베이스를 변경해도 코드 수정할 필요 없음(oracle이면 자동으로 SEQUENCE 선택)
	@Column(name = "seq_no")
	private Long seqNo;

	@Column(name = "serial_no")
	private String serialNo;

	@Column(name = "start_no")
	private String startNo;

	@Column(name = "end_no")
	private String endNo;

	@Column(name = "svc_cd")
	private String serviceCode;


	@Builder
	public BandAssign(Long seqNo, String serialNo, String start_no, String endNo, String serviceCode) {
		this.seqNo = seqNo;
		this.serialNo = serialNo;
		this.startNo = startNo;
		this.endNo = endNo;
		this.serviceCode = serviceCode;
	}

	public BandAssign() {}

}

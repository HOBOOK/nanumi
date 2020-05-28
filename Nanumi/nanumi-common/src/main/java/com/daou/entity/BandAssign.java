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

	/**
	 * com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Table 'DB명.hibernate_sequence' doesn't exist
	 * @GeneratedValue(strategy = GenerationType.AUTO) 오류
	 * hibernate ver4 -> 5 버전업 되면서 문제 발생
	 * Auto 지정시 자동으로 DB명.hibernate_sequence 생성하려함
	 * mysql,mariaDB 환경에서는 @GeneratedValue(strategy = GenerationType.IDENTITY) 사용
	 */

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)	//데이터베이스를 변경해도 코드 수정할 필요 없음(oracle이면 자동으로 SEQUENCE 선택)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq_no")
	private Long seqNo;

	@Column(name = "serial_no")
	private String serialNo;

	@Column(name = "start_no")
	private String startNo;

	@Column(name = "end_no")
	private String endNo;

	@Column(name = "svc_id")
	private String svcId;


	@Builder
	public BandAssign(Long seqNo, String serialNo, String startNo, String endNo, String svcId) {
		this.seqNo = seqNo;
		this.serialNo = serialNo;
		this.startNo = startNo;
		this.endNo = endNo;
		this.svcId = svcId;
	}

	public BandAssign() {}

}

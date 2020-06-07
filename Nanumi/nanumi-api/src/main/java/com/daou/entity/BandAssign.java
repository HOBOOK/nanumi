package com.daou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Song
 */

/**
 * @NoArgsConstructor는 기본 생성자
 * @AllArgsConstructor는 필드값을 모두 포함한 생성자 자동 생성
 *
 * @DynamicInsert, @DynamicUpdate
 * set 하지 않은 변수(Null) Insert, update 시 자동생성되는 SQL에서 제외
 */
@Data
@Entity
@Table(name="band_assign_tb")
public class BandAssign {

	/**
	 * com.mysql.jdbc.exceptions.jdbc.MySQLSyntaxErrorException: Table 'DB명.hibernate_sequence' doesn't exist
	 * @GeneratedValue(strategy = GenerationType.AUTO) 오류
	 * hibernate ver.4 -> 5 버전업 되면서 문제 발생
	 * Auto 지정시 자동으로 DB명.hibernate_sequence 생성하려함
	 * mysql,mariaDB 환경에서는 @GeneratedValue(strategy = GenerationType.IDENTITY) 사용
	 */

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)	//데이터베이스를 변경해도 코드 수정할 필요 없음(oracle이면 자동으로 SEQUENCE 선택)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq_no", insertable = false, updatable = false)
	private Long seqNo;

	/**
	 * SAVE시 모든컬럼 INSERT, NOT NULL로 설정된 컬럼은 기본값 X NULL로 삽입 시도함
	 * ㄴ 쿼리에서 컬럼 제외 insertable = false, updatable = false
	 * 쿼리에서 제외된 컬럼은 default값 삽입됨.
	 */
	@Column(name = "serial_no")
	private String serialNo;

	@Column(name = "start_no")
	private String startNo;

	@Column(name = "end_no")
	private String endNo;

	@Column(name = "svc_id", updatable = false)
	private String svcId;

	@Column(name = "assign_dt", nullable = false, insertable = false, updatable = false)
	private LocalDateTime assignDt;

	@Column(name = "expire_dt", nullable = false, insertable = false, updatable = false)
	private LocalDateTime expireDt;

	@Column(name = "status", nullable = false, insertable = false)
	private byte status;

	@ManyToOne
	@JoinColumn(name = "svc_id", insertable = false, updatable = false, nullable = false)
	@JsonIgnore
	private Account account;

//	@ManyToMany
//	@JoinColumn(name = "serial_no", insertable = false, updatable = false, nullable = false)
//	@JsonIgnore
//	private Band band;

	@Builder
	public BandAssign(Long seqNo, String serialNo, String startNo, String endNo, String svcId, LocalDateTime assignDt, LocalDateTime expireDt, byte status) {
		this.seqNo = seqNo;
		this.serialNo = serialNo;
		this.startNo = startNo;
		this.endNo = endNo;
		this.svcId = svcId;
		this.assignDt = assignDt;
		this.expireDt = expireDt;
		this.status = status;
	}

	public BandAssign() {}

//	public String toString() {
//		return String.format("%s %s %s %s %s, ", serialNo,svcId,startNo,endNo,status);
//	}
}

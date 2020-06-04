package com.daou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Song
 */

@Data
@Entity
@Table(name="BAND_RECV_TB")
public class BandReceive {
	@Id	//엔티티 클래스 PK값 지정
	@GeneratedValue(strategy = GenerationType.AUTO) //PK 생성 전략 설정 - 데이터베이스를 변경해도 코드 수정할 필요 없음(oracle이면 자동으로 SEQUENCE 선택)
	@Column(name = "seq_no")
	private Long seqNo;

	@Column(name = "serial_no")
	private String serialNo;

	@Column(name = "recv_no")
	private String receiveNo;

	@Column(name = "create_dt")
	private LocalDateTime createDate;

	@Column(name = "update_dt")
	private LocalDateTime updateDate;

	@Column(name = "svc_id")
	private String svcId;

	@Column(name = "user_id")
	private String svcUserId;

	
	//계정 테이블과 조인 필요
//	@ManyToOne // N:1 매핑
//	@JoinColumn(name = "id", insertable = false, updatable = false)
//	@JsonIgnore
//	private Account account;


	@ManyToOne // N:1 매핑
	@JoinColumn(name = "serial_no", insertable = false, updatable = false)
	@JsonIgnore
	private Band band;

	//insertable = false, updatable = false 읽기만 가능

	@Builder
	public BandReceive(Long seqNo, String serialNo, String receiveNo, LocalDateTime createDate,
					   LocalDateTime updateDate, String svcId, String svcUserId) {
		this.seqNo = seqNo;
		this.serialNo = serialNo;
		this.receiveNo = receiveNo;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.svcId = svcId;
		this.svcUserId = svcUserId;
	}

	public BandReceive() {}
}

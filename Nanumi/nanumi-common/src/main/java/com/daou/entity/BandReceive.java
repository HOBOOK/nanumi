package com.daou.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Getter
@Entity
@Table(name="BAND_RECV_TB")
public class BandReceive {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	//데이터베이스를 변경해도 코드 수정할 필요 없음(oracle이면 자동으로 SEQUENCE 선택)
	@Column(name = "seq_no")
	private Long seqNo;

	@Column(name = "serial_no")
	private String serialNo;

	@Column(name = "recv_no")
	private String receiveNo;

	@Column(name = "update_dt")
	private LocalDate updateDate;

	@Column(name = "user_id")
	private String userId;

	@Builder
	public BandReceive(Long seqNo, String serialNo, String receiveNo, LocalDate updateDate, String userId) {
		this.seqNo = seqNo;
		this.serialNo = serialNo;
		this.receiveNo = receiveNo;
		this.updateDate = updateDate;
		this.userId = userId;
	}

	public BandReceive() {}
}

package com.daou.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Song
 */

@Data
@Entity
@Table(name="band_Log_tb")
public class BandLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	//데이터베이스를 변경해도 코드 수정할 필요 없음(oracle이면 자동으로 SEQUENCE 선택)
	@Column(name = "seq_log_no")
	private Long seqLogNo;

	@Column(name = "serial_no")
	private String serialNo;

	@Column(name = "rev_type")
	private byte revType;
	
	@Column(name = "update_dt")
	private String updateDate;

	@Builder
	public BandLog(Long seqLogNo, String serialNo, byte revType, String updateDate) {
		this.seqLogNo = seqLogNo;
		this.serialNo = serialNo;
		this.revType = revType;
		this.updateDate = updateDate;
	}
	
	public BandLog() {}

}

package com.daou.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

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

	@Column(name = "admin_id")
	private String adminId;

	@Column(name = "update_dt")
	private LocalDateTime updateDate;

	@Column(name = "rev_type")
	private byte revType;
	
	@Column(name = "description")
	private String description;

	@Builder
	public BandLog(Long seqLogNo, String serialNo, String adminId, byte revType, LocalDateTime updateDate, String description) {
		this.seqLogNo = seqLogNo;
		this.serialNo = serialNo;
		this.adminId = adminId;
		this.revType = revType;
		this.updateDate = updateDate;
		this.description = description;
	}
	
	public BandLog() {}

}

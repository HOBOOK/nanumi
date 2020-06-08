package com.daou.entity;

import com.daou.types.BandLogType;
import lombok.Builder;
import lombok.Data;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//데이터베이스를 변경해도 코드 수정할 필요 없음(oracle이면 자동으로 SEQUENCE 선택)
	@Column(name = "seq_log_no", insertable = false)
	private Long seqLogNo;

	@Column(name = "serial_no")
	private String serialNo;

	@Column(name = "update_dt" , insertable = false)
	private LocalDateTime updateDate;

	@Column(name = "rev_type")
	private BandLogType revType;
	
	@Column(name = "description")
	private String description;

	@Builder
	public BandLog(Long seqLogNo, String serialNo, BandLogType revType, LocalDateTime updateDate, String description) {
		this.seqLogNo = seqLogNo;
		this.serialNo = serialNo;
		this.revType = revType;
		this.updateDate = updateDate;
		this.description = description;
	}
	
	public BandLog() {}

}

package com.daou.entity;

import lombok.*;
import javax.persistence.*;

@Data
@Getter
@Entity
@Table(name="band_tb")
public class Band {

//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy = GenerationType.AUTO)
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
	@Enumerated(EnumType.STRING)
	private com.daou.types.category category;
	
	@Builder
	public Band(Long seqNo, String serialNo, String countryNo, String localNo, String baseNo
			, String startNo, String endNo, com.daou.types.category category) {
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
	
	public String toString() {
		return String.format("seqNo=%d serialNo=%s category=%s, ", seqNo,serialNo,category);
	}
}

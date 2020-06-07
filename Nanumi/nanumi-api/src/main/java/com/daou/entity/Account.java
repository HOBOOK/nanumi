package com.daou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자, 관리자 계정 엔티티
 * @author Song
 */

@Data
@Entity
@Table(name="account_tb")
public class Account extends Time{
	@Id
	@Column(name = "id", nullable = false, unique = true)
	private String id;

	@Column(name = "pwd", nullable = false)
	private String pwd;

	@Column(name = "svc_nm")
	private String svcNm;

	@Column(name = "role_cd", nullable = false)
	private String roleCd;

	@Column(name ="request_cnt")
	private int requestCnt;

	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<BandAssign> bandAssign = new ArrayList<BandAssign>();

	@Builder
	public Account(String id, String pwd, String svcNm, String roleCd, int requestCnt) {
		this.id = id;
		this.pwd = pwd;
		this.svcNm = svcNm;
		this.roleCd = roleCd;
		this.requestCnt = requestCnt;
	}

	public Account(){}
}

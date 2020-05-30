package com.daou.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

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

	@Column(name = "access_token")
	private String accessToken;

	@Column(name ="refresh_token")
	private String refreshToken;

	@Column(name = "expire_dt")
	private LocalDateTime expireDt;

	@Column(name = "role_cd", nullable = false)
	private String roleCd;

	@Builder
	public Account(String id, String pwd, String svcNm, String accessToken, String refreshToken, LocalDateTime expireDt, String roleCd) {
		this.id = id;
		this.pwd = pwd;
		this.svcNm = svcNm;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expireDt = expireDt;
		this.roleCd = roleCd;
	}

	public Account(){}
}

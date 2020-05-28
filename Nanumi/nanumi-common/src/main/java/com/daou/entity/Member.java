package com.daou.entity;

import lombok.*;
import javax.persistence.*;

/**
 * @author Song
 */

@Data
@Entity
@Table(name="member")
public class Member {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long mbrNo;
	
	@Column(name = "id")
	private String id;

	@Column(name = "pwd")
	private String pwd;
	
	@Column(name = "name")
	private String name;

	@Column(name = "role")
	private String role;

	@Builder
	public Member(String id, String pwd, String name, String role) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.role = role;
	}
	
	public Member() {}
	
	public String toString() {
		return String.format("mbrNo=%d id=%s name=%s, ", mbrNo,id,name);
	}
}

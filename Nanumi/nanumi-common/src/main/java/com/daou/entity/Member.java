package com.daou.entity;

import lombok.*;
import javax.persistence.*;

@Data
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity(name="member")
@Entity
@Table(name="member")
public class Member {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long mbrNo;
	
	@Column(name = "id")
	private String id;
	
	@Column(name = "name")
	private String name;

	@Builder
	public Member(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Member() {}
	
	public String toString() {
		return String.format("mbrNo=%d id=%s name=%s, ", mbrNo,id,name);
	}
}

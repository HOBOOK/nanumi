package com.daou.types;

import javax.persistence.Column;

/**
 * @author Song
 */

public enum category {
	NONE,	//카테고리가 지정되지 않은 초기상태
	FAX,	//카테고리 FAX 지정
	MO,		//카테고리 MO 지정
	ZEZ;	//ZEZ : 080 (추가 예정)
}
package com.daou.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 데이터 입력, 갱신 날짜 추상 클래스
 * @author pkh879
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Time {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime create_dt;

    @LastModifiedDate
    private LocalDateTime update_dt;
}

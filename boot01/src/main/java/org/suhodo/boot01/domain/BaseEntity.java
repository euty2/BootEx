package org.suhodo.boot01.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

/*
 * 앞으로 다른 테이블 클래스의 기본이 되는 부모 테이블 클래스
 * @EntityListeners(value = {AuditingEntityListener.class})는
 * regdate : 데이터 처음 등록 시간
 * moddate : 데이터 수정 시간(계속 변경)
 * 을 하려면 main에 @EnableJpaAuditing을 해야 한다.
 */

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;
}

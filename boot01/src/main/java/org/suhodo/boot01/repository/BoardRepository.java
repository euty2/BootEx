package org.suhodo.boot01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.suhodo.boot01.domain.Board;

/*
 * BoardRepository는 Long(pk의 자료형)
 * Board테이블을 삽입/삭제/수정/조회하는 데 사용
 * 기본 기능은 상속만 받아도 자동 구현
 * Spring Boot에 의해서 
 * 스프링 컨테이너에 BoardRepository의 자식 객체가 Bean으로 등록된다.
 */
public interface BoardRepository extends JpaRepository<Board, Long> {

}
package org.suhodo.boot01.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.suhodo.boot01.domain.Board;
import org.suhodo.boot01.repository.search.BoardSearch;

/*
 * BoardRepository는 Long(pk의 자료형)
 * Board테이블을 삽입/삭제/수정/조회하는 데 사용
 * 기본 기능은 상속만 받아도 자동 구현
 * Spring Boot에 의해서 
 * 스프링 컨테이너에 BoardRepository의 자식 객체가 Bean으로 등록된다.
 */
public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
    // 기본 CRUD 기능을 자동으로 제공한다.
    // 그런데 사용자의 모든 SQL에 대응할 수 없으므로 확장기능이 필요하다.

    // 1) 쿼리 메서드
    //    메서드 작명법(단어명명)에 따른 규칙을 JPQL -> SQL로 전환한다.
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);

    // 2) JPQL(JPA 표준 SQL)
    // JPA에서 사용하는 SQL
    // 연결된 물리 DBMS에 따라 해당 Native SQL로 변환된다.
    // Native SQL보다 사용이 권장된다.

    // EntityGraph는 아래 findByIdWithImages메서드를 호출해서 Board의 정보를 가져올 때
    // imageSet의 정보도 함께 가져오라는 의미 
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select b from Board b where b.bno = :bno")
    Optional<Board> findByIdWithImages(@Param("bno") Long bno);

    // 3) Native SQL
    // 직접 물리적 DBMS에 해당하는 쿼리를 작성
    // 비추천(왜냐하면 DBMS에 종속적이 되므로 이식성 나빠짐)
    @Query(value = "select now()", nativeQuery=true)
    String getTime();

    // 4) Querydsl
    // 복잡한 쿼리를 생성하기 위한 메서드의 조합
    // Q + 'Entity명' 클래스를 생성해야 한다.
    /*
     * vscode -> 새창 -> 폴더열기(현재 프로젝트)
     * build.gradle -> Show Tasks
     * Tasks -> other -> compileJava를 진행하면
     * Q 클래스가 생긴다.
     * BOOT01 -> build -> classes -> java -> ... -> domain -> QBaseEntity.class, QBoard.class
     */
}
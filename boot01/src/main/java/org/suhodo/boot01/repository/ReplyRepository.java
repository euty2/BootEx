package org.suhodo.boot01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.suhodo.boot01.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{

    // JPQL(JPA에서 사용하는 표준 SQL)
    @Query("select r from Reply r where r.board.bno = :bno")
    Page<Reply> listOfBoard(@Param("bno") Long bno, Pageable pageable);

    // 쿼리 메서드
    void deleteByBoard_Bno(Long bno);
}

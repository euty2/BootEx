package org.suhodo.boot01.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.suhodo.boot01.domain.Board;
import org.suhodo.boot01.domain.QBoard;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

/*
 * QueryDsl을 사용하는 방법
 * 1) 인터페이스 정의 : BoardSearch
 * 2) 클래스 정의 : BoardSearchImpl(인터페이스명 + Impl)
 *                 상속: QuerydslRepositorySupport, BoardSearch
 * 3) BoardRepository에 BoardSearch를 상속시킴
 */
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        // 이곳에 QueryDsl방식으로 구성한다.

        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);       // FROM board

        query.where(board.title.contains("1")); // WHERE title LIKE '%1%'

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);       // FROM board

        if((types!=null && types.length>0) && keyword!=null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type: types){
                switch(type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            /*
             * WHERE title LIKE '%{keyword}%'
             *    OR content LIKE '%{keyword}'
             *    OR writer LIKE '%{keyword}'
             */
            
            query.where(booleanBuilder);
        }

        // WHERE bno > 0
        query.where(board.bno.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }    
}
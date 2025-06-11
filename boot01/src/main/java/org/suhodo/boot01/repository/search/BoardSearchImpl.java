package org.suhodo.boot01.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.suhodo.boot01.domain.Board;
import org.suhodo.boot01.domain.QBoard;
import org.suhodo.boot01.domain.QReply;
import org.suhodo.boot01.dto.BoardImageDTO;
import org.suhodo.boot01.dto.BoardListAllDTO;
import org.suhodo.boot01.dto.BoardListReplyCountDTO;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

/*
 * QueryDsl을 사용하는 방법
 * 1) 인터페이스 정의 : BoardSearch
 * 2) 클래스 정의 : BoardSearchImpl(인터페이스명 + Impl)
 *                 상속: QuerydslRepositorySupport, BoardSearch
 * 3) BoardRepository에 BoardSearch를 상속시킴
 */
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        // 이곳에 QueryDsl방식으로 구성한다.

        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board); // FROM board

        query.where(board.title.contains("1")); // WHERE title LIKE '%1%'

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board); // FROM board

        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {
                switch (type) {
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
             * OR content LIKE '%{keyword}'
             * OR writer LIKE '%{keyword}'
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

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);   // from board
        query.leftJoin(reply).on(reply.board.eq(board)); // left join reply on board.bno=reply.board_bno

        query.groupBy(board);                   // group by board

        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {
                switch (type) {
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
            // where board.title like '%{keyword}%' or
            //      board.content like '%{keyword}%' or
            //      board.writer like '%{keyword}%'
            query.where(booleanBuilder);
        }

        JPQLQuery<BoardListReplyCountDTO> dtoQuery = 
                query.select(Projections.bean(BoardListReplyCountDTO.class,
                    board.bno,
                    board.title,
                    board.writer,
                    board.regDate,
                    reply.count().as("replyCount")));

        this.getQuerydsl().applyPagination(pageable, dtoQuery);
        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithAll1(String[] types, String keyword, Pageable pageable) {
        
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        /*
         * SELECT * FROM board
         * LEFT JOIN reply ON board.bno=reply.bno;
         */
        JPQLQuery<Board> boardJQPLQuery = from(board);
        boardJQPLQuery.leftJoin(reply).on(reply.board.eq(board));

        getQuerydsl().applyPagination(pageable, boardJQPLQuery);

        List<Board> boardList = boardJQPLQuery.fetch();

        boardList.forEach(board1 -> {
            System.out.println(board1.getBno());
            System.out.println(board1.getImageSet());   // Board에 종속된 BoardImage도 가져옴
            System.out.println("-------------------------------");
        });

        return null;
    }

    @Override
    public Page<BoardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> boardJPQLQuery = from(board);
        boardJPQLQuery.leftJoin(reply).on(reply.board.eq(board));

        // 검색 조건 추가
        if((types != null && types.length > 0) && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type : types){
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
            boardJPQLQuery.where(booleanBuilder);
        }

        boardJPQLQuery.groupBy(board);

        getQuerydsl().applyPagination(pageable, boardJPQLQuery);

        // board정보 요청, reply갯수 요청
        JPQLQuery<Tuple> tupleJPQLQuery = boardJPQLQuery.select(board, reply.countDistinct());

        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<BoardListAllDTO> dtoList = tupleList.stream().map(tuple -> {

            Board board1 = (Board)tuple.get(board); // tuple.get(0, board);
            long replyCount = tuple.get(1, Long.class);

            BoardListAllDTO dto = BoardListAllDTO.builder()
                            .bno(board1.getBno())
                            .title(board1.getTitle())
                            .writer(board1.getWriter())
                            .regDate(board1.getRegDate())
                            .replyCount(replyCount)
                            .build();

            // BoardImage를 BoardImageDTO 처리할 부분
            List<BoardImageDTO> imageDTOS = board1.getImageSet().stream().sorted()
                    .map(boardImage -> BoardImageDTO.builder()
                    .uuid(boardImage.getUuid())
                    .fileName(boardImage.getFileName())
                    .ord(boardImage.getOrd())
                    .build()
                    ).collect(Collectors.toList());

            dto.setBoardImages(imageDTOS);

            return dto;
        }).collect(Collectors.toList());

        long totalCount = boardJPQLQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, totalCount);
    }
}

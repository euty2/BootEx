package org.suhodo.boot01.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.suhodo.boot01.domain.Board;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                        .title("title..." + i)
                        .content("content..." + i)
                        .writer("user" + (i % 10))
                        .build();

            Board result = boardRepository.save(board);
            log.info("BNO: " + result.getBno());                        
        });
    }

    @Test
    public void testSelect(){
        Long bno = 100L;

        // 결과값이 null일 수도 있고, 아닐 수도 있을 때
        // 결과값이 null이 아닌지 확인해서 안전한 프로그램 진행을
        // 도와주는 클래스
        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info(board);
    }

    @Test
    public void testUpdate(){
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        board.change("update..title 100", "update content 100");

        boardRepository.save(board);
    }

    @Test
    public void testDelete(){
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }
}

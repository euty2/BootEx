package org.suhodo.boot01.repository;

import static org.mockito.ArgumentMatchers.isNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.suhodo.boot01.domain.Board;
import org.suhodo.boot01.domain.BoardImage;
import org.suhodo.boot01.dto.BoardListReplyCountDTO;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

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

    @Test
    public void testPaging(){
        Pageable pageable = 
        PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("page size: " + result.getSize());

        List<Board> todoList = result.getContent();

        todoList.forEach(board->log.info(board));
    }

    @Test
    public void testTime(){
        String nowTime = boardRepository.getTime();

        log.info("현재 시간: " + nowTime);
    }
    
    @Test
    public void testSearch1(){
        Pageable pageable = PageRequest.of(1, 10,
                                    Sort.by("bno").descending());

        boardRepository.search1(pageable);
    }

    @Test
    public void testSearchAll(){
        String[] types = {"t", "c", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        log.info(result.getTotalPages());       // 전체 페이지
        log.info(result.getSize());             // 전체 갯수
        log.info(result.getNumber());           // 현재 페이지
        log.info(result.hasPrevious() + " : " + result.hasNext());
        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testSearchReplyCount(){
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        log.info(result.getTotalPages());
        log.info(result.getSize());
        log.info(result.getNumber());
        log.info(result.hasPrevious() + " : " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testInsertWithImages(){
        Board board = Board.builder()
                    .title("Image Test")
                    .content("첨부파일 테스트")
                    .writer("tester")
                    .build();

        for(int i=0;i<3;i++){
            board.addImage(UUID.randomUUID().toString(), "file" + i + ".jpg");
        }

        boardRepository.save(board);
    }

    @Transactional  // No Session, 중간에 세션 연결이 중지되는 것을 방지할 때 사용함 
    @Test
    public void testReadWithImages(){
        Optional<Board> result = boardRepository.findById(109L);

        Board board = result.orElseThrow();

        log.info(board);
        log.info("------------------");
        log.info(board.getImageSet());
    }

    @Test
    public void testReadWithImages1(){
        Optional<Board> result = boardRepository.findByIdWithImages(109L);

        Board board = result.orElseThrow();

        log.info(board);
        log.info("---------------------");
        for(BoardImage boardImage : board.getImageSet()){
            log.info(boardImage);
        }
    }

    @Transactional
    @Commit
    @Test
    public void testModifyImages(){
        Optional<Board> result = boardRepository.findByIdWithImages(109L);

        Board board = result.orElseThrow();

        /*  모두 삭제 시도
          Board와 BoardImage의 연결은 끊어지지만,
          실제 BoardImage가 삭제되지 않는다.

          실제 삭제하고 싶다면 Board의 imageSet에 
          orphanRemoval = true 설정을 해야 한다.
        */
        board.clearImages();        

        for(int i=0;i<2;i++){
            board.addImage(UUID.randomUUID().toString(), "updatefile" + i + ".jpg");
        }

        boardRepository.save(board);
    }

    @Test
    @Transactional
    @Commit
    public void testRemoveAll(){
        Long bno = 109L;

        replyRepository.deleteByBoard_Bno(bno);     // bno에 종속된 댓글 삭제
        boardRepository.deleteById(bno);            // 게시글과 종속된 BoardImage 삭제
    }

    @Test
    public void testInsertAll(){
        for(int i=0;i<=100;i++){
            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("writer..." + i)
                    .build();

            for(int j=0;j<3;j++){
                if(i%5 == 0)
                    continue;
                
                board.addImage(UUID.randomUUID().toString(), i+"file"+j+".jpg");
            }

            boardRepository.save(board);
        }
    }

    @Transactional
    @Test
    public void testSearchImageReplyCount(){
        Pageable pageable = 
            PageRequest.of(0, 10, Sort.by("bno").descending());

        boardRepository.searchWithAll(null, null, pageable);
    }
}

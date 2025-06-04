package org.suhodo.boot01.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.suhodo.boot01.domain.Board;
import org.suhodo.boot01.dto.BoardDTO;
import org.suhodo.boot01.repository.BoardRepository;

import groovy.util.logging.Log4j2;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service                    // Service 계층 역할 담당
@Log4j2                     // 로그 출력
@RequiredArgsConstructor    // Bean을 주입할 때 생성자를 통해 주입
@Transactional              // DBMS을 입출력시 함수 종료 전에 트랜잭션이 유지되도록
public class BoardServiceImpl implements BoardService {
    /*
     * Spring Container의 Bean을 주입하는 2가지 방법
     * 1) @Autowired
     *    ; JUnit 테스트시에 주로 사용
     *      main영역에서는 상호참조의 오류가 발생하는 경우가 종종 있어서 주로 2)번을 사용한다.
     * 2) @RequiredArgsConstructor설정과
     *    주입할 변수에 private final을 설정해야 한다.
     */
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO boardDTO) {
        // BoardDTO -> board로 변환
        Board board = modelMapper.map(boardDTO, Board.class);

        Long bno = boardRepository.save(board).getBno();
        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow();
        board.change(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);
    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

}

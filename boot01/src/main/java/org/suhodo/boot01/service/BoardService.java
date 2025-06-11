package org.suhodo.boot01.service;

import java.util.List;
import java.util.stream.Collectors;

import org.suhodo.boot01.domain.Board;
import org.suhodo.boot01.dto.BoardDTO;
import org.suhodo.boot01.dto.BoardListAllDTO;
import org.suhodo.boot01.dto.BoardListReplyCountDTO;
import org.suhodo.boot01.dto.PageRequestDTO;
import org.suhodo.boot01.dto.PageResponseDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO);
    BoardDTO readOne(Long bno);
    void modify(BoardDTO boardDTO);
    void remove(Long bno);

    // 댓글 갯수 없는 게시판 리스트
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    // 댓글 갯수 포함한 게시판 리스트
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

    // 게시글의 이미지와 댓글의 숫자까지 처리
    PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);

    /*원래 interface는 추상메서드만 가능했다.
     * 그런데 빠른 개발 환경에 따라
     * 해당 interface를 상속받는 자식 클래스에 특정 신기능이 필요할 때
     * 상속받는 모든 자식에 직접 구현해야 한다는 불편한 점이 있었다.
     * 그래서 java는 고민 끝에 사용의 유연함을 가져오기로 결정을 했고,
     * 그래서 최신 java에서는 interface에 메서드를 구현하면
     * 모든 자식 클래스가 공통으로 사용하는 메서드로 작동하게 된다.
     */
     /* BoardDTO(MVC에서 주고 받는 클래스) <-> Board(DBMS와 연결된 Entity클래스)
      * 현재 BoardDTO의 필드가 Board의 필드보다 추가되었다.
      * 이럴 경우 ModelMapper로 자동변환은 오히려 불편하다.
      * 그래서 이것을 변환해주는 메서드를 직접 구현한다.
      */
    default Board dtoToEntity(BoardDTO boardDTO){

        Board board = Board.builder()
                    .bno(boardDTO.getBno())
                    .title(boardDTO.getTitle())
                    .content(boardDTO.getContent())
                    .writer(boardDTO.getWriter())
                    .build();

        if(boardDTO.getFileNames()!=null){
            boardDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                board.addImage(arr[0], arr[1]);
            });
        }

        return board;
    }

    default BoardDTO entityToDTO(Board board){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        List<String> fileNames = board.getImageSet().stream().sorted().map(boardImage ->
                boardImage.getUuid() + "_" + boardImage.getFileName())
                .collect(Collectors.toList());

        boardDTO.setFileNames(fileNames);

        return boardDTO;
    }
}

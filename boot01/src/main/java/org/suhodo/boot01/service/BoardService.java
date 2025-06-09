package org.suhodo.boot01.service;

import org.suhodo.boot01.dto.BoardDTO;
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
}

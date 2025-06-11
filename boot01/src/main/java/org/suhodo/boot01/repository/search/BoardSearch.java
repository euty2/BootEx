package org.suhodo.boot01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.suhodo.boot01.domain.Board;
import org.suhodo.boot01.dto.BoardListAllDTO;
import org.suhodo.boot01.dto.BoardListReplyCountDTO;

public interface BoardSearch {

    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types,
                                                String keyword, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithAll1(String[] types, String keyword, Pageable pageable);

    Page<BoardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);
}

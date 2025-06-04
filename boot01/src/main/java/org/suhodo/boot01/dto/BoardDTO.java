package org.suhodo.boot01.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO(Data Transfer Object)
 * 각 계층간에 데이터를 전달할 때
 * 이 클래스 객체로 묶어서 전달한다.
 * DBMS에 저장하기 전에 BoardDTO -> Board로 변환
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}

package org.suhodo.boot01.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO(Data Transfer Object)
 * 각 계층간에 데이터를 전달할 때
 * 이 클래스 객체로 묶어서 전달한다.
 * DBMS에 저장하기 전에 BoardDTO -> Board로 변환되어진다.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long bno;

    @NotEmpty
    @Size(min=3, max=100)
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String writer;
    
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    // 첨부 파일의 이름들
    private List<String> fileNames;
}

package org.suhodo.boot01.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Reply", indexes = {
    @Index(name="idx_reply_board_bno", columnList="board_bno")
})  // board_bno는 fk이므로 인덱스가 자동으로 생성되지 않아, 인덱스를 만들어준다.
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    /* @ManyToOne
       Reply가 n, Board가 1인 관계
     * 
     * N:1관계를 정의할 때 JPA에서는 자식Entity에서 @ManyToOne을 주로 사용한다.
     * 
     * EARGER : 즉시 가져오다
     * LAZY : 나중에 요청하면 가져오다(지연된다)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String replyText;

    private String replyer;

    public void changeText(String text){
        this.replyText = text;
    }
}

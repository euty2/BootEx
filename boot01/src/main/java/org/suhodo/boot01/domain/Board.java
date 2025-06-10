package org.suhodo.boot01.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity     // 테이블이 생성된다.
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Board extends BaseEntity{
    @Id     // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long bno;

    @Column(length=500, nullable=false)
    private String title;

    @Column(length=2000, nullable = false)
    private String content;

    @Column(length=50, nullable = false)
    private String writer;

    // @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    @OneToMany
    @Builder.Default
    private Set<BoardImage> imageSet = new HashSet<>();

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
}

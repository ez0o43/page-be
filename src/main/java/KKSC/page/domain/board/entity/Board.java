package KKSC.page.domain.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 게시글 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false) // 게시글 고유 ID
    private Long id;

    @NotBlank
    @Column(name = "title", nullable = false) // 게시글 제목
    private String title;

    @NotBlank
    @Column(name = "content", nullable = false) // 게시글 내용
    private String content;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments; // 게시글의 달린 댓글 목록

    @Builder
    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * 게시글을 수정하는 메소드
     *
     * @param title   새 제목
     * @param content 새 내용
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

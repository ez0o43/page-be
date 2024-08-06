package KKSC.page.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 댓글 엔티티
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글의 고유 ID

    @Column(nullable = false) // 댓글 내용이 null이 되지 않도록 설정
    private String content; // 댓글의 내용

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩을 설정하여 성능 최적화
    @JoinColumn(name = "board_id", nullable = false) // 댓글이 달린 게시글을 참조
    private Board board; // 댓글이 달린 게시글

    /**
     * 댓글 엔티티의 생성자
     *
     * @param content 댓글 내용
     * @param board 댓글이 달린 게시글
     */
    public Comment(String content, Board board) {
        this.content = content; // 댓글의 내용 설정
        this.board = board;     // 댓글이 달린 게시글 설정
    }

    /**
     * 댓글이 달린 게시글을 설정하는 메소드
     *
     * @param board 댓글이 달린 게시글
     */
    public void setBoard(Board board) {
        // 기존에 연결된 게시글이 있을 경우
        if (this.board != null) {
            // 기존 게시글의 댓글 목록에서 현재 댓글을 제거합니다.
            this.board.getComments().remove(this);
        }

        // 현재 댓글의 게시글을 새로운 게시글로 설정합니다.
        this.board = board;

        // 새로운 게시글이 null이 아니고, 해당 게시글의 댓글 목록에 현재 댓글이 포함되어 있지 않은 경우
        if (board != null && !board.getComments().contains(this)) {
            // 새로운 게시글의 댓글 목록에 현재 댓글을 추가합니다.
            board.getComments().add(this);
        }
    }
}
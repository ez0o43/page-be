package KKSC.page.domain.board.repository;

import KKSC.page.domain.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 댓글 관련 데이터베이스 작업을 처리하는 레포지토리
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * 게시글 ID로 댓글 조회
     *
     * @param boardId 게시글의 ID
     * @return 게시글에 달린 댓글 목록
     */
    List<Comment> findByBoardId(Long boardId);
}

package KKSC.page.domain.notice.entity;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeBoard extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_board_id")
    private Long id;

//    private Member member;

    @OneToMany(mappedBy = "noticeBoard")
    private List<NoticeFile> noticeFiles;

    @Enumerated(EnumType.STRING)
    private Keyword keyword; /* (제목, 내용, 작성자)로 검색 */

    private String title;
    private String content;

    private Long view; /* 조회수 */
    private Long fixed; /* 상단 고정 여부 */
    private Long delYN; /* 삭제 여부 */

    /* 게시글 수정 메서드 */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        updateModifitedAt();
    }
}

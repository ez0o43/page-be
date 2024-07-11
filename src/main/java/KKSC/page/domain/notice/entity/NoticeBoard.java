package KKSC.page.domain.notice.entity;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

//    @OneToMany(mappedBy = "noticeBoard")
//    private List<NoticeFile> noticeFiles;

    @Enumerated(EnumType.STRING)
    private Keyword keyword; /* (제목, 내용, 작성자)로 검색. default 제목 */

    private String title;
    private String content;

    private Long view; /* 조회수 */
    private Long fixed; /* 상단 고정 여부 */
    private Long delYN; /* 삭제 여부 */

    public void update(NoticeBoardRequest noticeBoardRequest) {
        this.title = noticeBoardRequest.title();
        this.content = noticeBoardRequest.content();
        this.fixed = noticeBoardRequest.fixed();
    }

    public void delete() {
        this.delYN = 1L;
    }
}

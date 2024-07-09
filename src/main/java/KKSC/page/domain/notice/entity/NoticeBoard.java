package KKSC.page.domain.notice.entity;

import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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

//    private File file;

    private String title;
    private String content;

    private Long view; /* 조회수 */
    private Long fixed; /* 상단 고정 여부 */

    @Setter
    private Long delYN; /* 삭제 여부 */

    public void update(NoticeBoardRequest noticeBoardRequest) {
        this.title = noticeBoardRequest.title();
        this.content = noticeBoardRequest.content();
        this.fixed = noticeBoardRequest.fixed();
    }
}

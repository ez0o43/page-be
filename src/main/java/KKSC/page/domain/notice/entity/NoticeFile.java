package KKSC.page.domain.notice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import KKSC.page.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFile extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_board_id")
    private NoticeBoard noticeBoard;

    private String name; /* 사용자가 보는 파일명 */

    private String baseURL; /* 서버에 해당 파일 주소 */

    private Long fileSize; /* 해당 파일 용량 */

    private String type; /* 해당 파일 타입 */

    @ColumnDefault("=0")
    private Long downloadCnt; /* 다운로드 횟수 default 0 */
    
}

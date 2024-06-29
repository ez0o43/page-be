package KKSC.page.domain.notice.entity;

import KKSC.page.global.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFIle extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; /* 파일명 */

    private String baseURL;

    private Long fileSize;

    private String type; /* txt, pdf, hwp etc.. */

    private Long downloadCnt; /* 다운로드 회수 */
}

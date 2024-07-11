package KKSC.page.domain.notice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;

import KKSC.page.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeFile extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 파일명 고유번호
    private String nameUuid;
    // 사용자가 보는 파일명
    private String name;

    // 서버에 해당 파일 주소
    private String baseUrl;

    // 해당 파일 용량
    private Long fileSize;

    // 해당 파일 타입
    private String filetype;

    // 다운로드 횟수 default 0
    @Column(columnDefinition = "integer default 0")
    private int downloadCnt;
}
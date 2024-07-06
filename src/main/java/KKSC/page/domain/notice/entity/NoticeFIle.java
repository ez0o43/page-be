package KKSC.page.domain.notice.entity;

import org.hibernate.annotations.ColumnDefault;

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

    // 사용자가 보는 파일명
    private String name; 

    // 서버에 해당 파일 주소
    private String baseURL;

    // 해당 파일 용량
    private Long fileSize;

    // 해당 파일 타입 
    private String type; 

    // 다운로드 횟수 default 0 
    @ColumnDefault("=0")
    private Long downloadCnt;
    
}

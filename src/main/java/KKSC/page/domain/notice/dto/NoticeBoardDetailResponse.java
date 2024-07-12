package KKSC.page.domain.notice.dto;

import KKSC.page.domain.notice.entity.NoticeBoard;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@ToString
public class NoticeBoardDetailResponse {

    private String title;
    private String content;
    private String createdBy;
    private Long view;
    private Long delYN;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    private List<NoticeFileResponse> noticeFileResponses;

    public static NoticeBoardDetailResponse fromEntity(NoticeBoard noticeBoard, List<NoticeFileResponse> noticeFileResponses) {
        return NoticeBoardDetailResponse.builder()
                .title(noticeBoard.getTitle())
                .content(noticeBoard.getContent())
                .createdBy(noticeBoard.getMemberName())
                .view(noticeBoard.getView())
                .delYN(noticeBoard.getDelYN())
                .createdAt(noticeBoard.getCreatedAt())
                .modifiedAt(noticeBoard.getModifiedAt())
                .noticeFileResponses(noticeFileResponses)
                .build();
    }
}
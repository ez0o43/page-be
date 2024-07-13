package KKSC.page.domain.notice.dto;

import KKSC.page.domain.notice.entity.NoticeBoard;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record NoticeBoardListResponse (
        String title,
        String createdBy,
        Long fileYN,
        Long view,
        Long delYN,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {
    public static NoticeBoardListResponse fromEntity(NoticeBoard noticeBoard, List<NoticeFileResponse> noticeFileResponses) {
        long fileYN = 1L;

        if (noticeFileResponses.isEmpty()) fileYN = 0L;

        return new NoticeBoardListResponse(
                noticeBoard.getTitle(),
                noticeBoard.getMemberName(),
                fileYN,
                noticeBoard.getView(),
                noticeBoard.getDelYN(),
                noticeBoard.getCreatedAt()
        );
    }
}
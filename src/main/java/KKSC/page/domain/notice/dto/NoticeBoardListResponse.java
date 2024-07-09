package KKSC.page.domain.notice.dto;

import KKSC.page.domain.notice.entity.NoticeBoard;

import java.time.LocalDateTime;

public record NoticeBoardListResponse (
        String title,
//        String name,
//        Long fileYN,
        Long view,
        Long delYN,
        LocalDateTime createdAt
) {
    // entity ->  dto
    public NoticeBoardListResponse(NoticeBoard noticeBoard) {
        this(
                noticeBoard.getTitle(),
                noticeBoard.getView(),
                noticeBoard.getDelYN(),
                noticeBoard.getCreatedAt()
        );
    }
}
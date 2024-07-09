package KKSC.page.domain.notice.dto;

import KKSC.page.domain.notice.entity.NoticeBoard;

import java.time.LocalDateTime;

public record NoticeBoardDetailResponse(
        String title,
        String content,
//        String createdBy,
        Long view,
        Long delYN,
//        Long fileId,
//        String fileName,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    // entity -> dto
    public NoticeBoardDetailResponse (NoticeBoard noticeBoard){
        this (
                noticeBoard.getTitle(),
                noticeBoard.getContent(),
                noticeBoard.getView(),
                noticeBoard.getDelYN(),
                noticeBoard.getCreatedAt(),
                noticeBoard.getModifiedAt()
        );
    }
}
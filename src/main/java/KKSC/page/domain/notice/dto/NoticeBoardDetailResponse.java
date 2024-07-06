package KKSC.page.domain.notice.dto;

import java.time.LocalDateTime;

public record NoticeBoardDetailResponse(
        String title,
        String content,
        String createdBy,
        Long view,
        Long delYN,
        Long fileId,
        String fileName,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {}
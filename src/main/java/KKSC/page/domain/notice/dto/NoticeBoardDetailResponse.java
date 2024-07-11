package KKSC.page.domain.notice.dto;

import java.time.LocalDateTime;
import java.util.List;

public record NoticeBoardDetailResponse(
        String title,
        String content,
        String createdBy,
        Long view,
        Long delYN,
        List<NoticeFileResponse> fileResponses,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}
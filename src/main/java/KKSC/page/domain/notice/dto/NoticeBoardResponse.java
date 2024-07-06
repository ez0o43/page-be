package KKSC.page.domain.notice.dto;

import java.time.LocalDateTime;

public record NoticeBoardResponse(
        String title,
        String content,
        Long view,
        Long fixed,
        Long delYN,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
}

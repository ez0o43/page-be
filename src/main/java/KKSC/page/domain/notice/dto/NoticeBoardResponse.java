package KKSC.page.domain.notice.dto;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.notice.entity.NoticeBoard;

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

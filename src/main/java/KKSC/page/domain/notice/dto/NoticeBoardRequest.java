package KKSC.page.domain.notice.dto;

import KKSC.page.domain.notice.entity.NoticeBoard;

public record NoticeBoardRequest(
        String title,
        String content,
        Long fixed) {

    public NoticeBoard toEntity() {
        return NoticeBoard.builder()
                .title(title()).content(content()).fixed(fixed()).build();
    }
}

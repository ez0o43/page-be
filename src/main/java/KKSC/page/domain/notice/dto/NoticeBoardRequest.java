package KKSC.page.domain.notice.dto;

import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.entity.NoticeBoard;

import java.util.ArrayList;

public record NoticeBoardRequest(
        String title,
        String content,
        Long fixed) {

    public NoticeBoard toEntity(String memberName) {
        return NoticeBoard.builder()
                .title(title()).content(content()).fixed(fixed())
                .keyword(Keyword.TITLE).delYN(0L)
                .view(0L).memberName(memberName)
                .noticeFiles(new ArrayList<>())
                .build();
    }
}
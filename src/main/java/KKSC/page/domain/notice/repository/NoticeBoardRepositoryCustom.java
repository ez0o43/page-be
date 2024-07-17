package KKSC.page.domain.notice.repository;

import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.entity.NoticeBoard;

import java.util.List;

public interface NoticeBoardRepositoryCustom {
    // 키워드 검색
    List<NoticeBoard> searchBoardList(Keyword keyword, String query);
}

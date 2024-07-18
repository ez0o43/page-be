package KKSC.page.domain.notice.repository;

import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.entity.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeBoardRepositoryCustom {

    // 키워드 검색
    List<NoticeBoard> searchBoardList(Keyword keyword, String query);

    // 페이징 처리
    Page<NoticeBoardListResponse> loadNoticeBoardList(Pageable pageable);
}

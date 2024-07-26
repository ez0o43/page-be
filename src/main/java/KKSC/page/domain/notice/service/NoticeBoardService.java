package KKSC.page.domain.notice.service;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.entity.Keyword;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeBoardService {

    Long create(NoticeBoardRequest noticeBoardRequest, String memberName);

    NoticeBoardDetailResponse update(Long noticeBoardId, NoticeBoardRequest noticeBoardRequest);

    void delete(Long noticeBoardId);

    Page<NoticeBoardListResponse> getBoardList(Pageable pageable); /* 기본적으로 보여줄 게시글 목록 */

    NoticeBoardDetailResponse getBoardDetail(Long noticeBoardId); /* 글 선택 시 보여줄 상세 글 */

    Page<NoticeBoardListResponse> searchBoardList(Keyword keyword, String query, Pageable pageable); /* 작성일 순, 작성자가 포함된 글 불러오기 */

    void countUpView(Long noticeBoardId);

    void readNotice(Long noticeBoardId, String cookieName, String cookieValue, HttpServletResponse response);
}

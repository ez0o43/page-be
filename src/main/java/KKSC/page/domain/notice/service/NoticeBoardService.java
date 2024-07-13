package KKSC.page.domain.notice.service;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;

import java.util.List;

public interface NoticeBoardService {

    void create(NoticeBoardRequest noticeBoardRequest, String memberName);

    NoticeBoardDetailResponse update(Long noticeBoardId, NoticeBoardRequest noticeBoardRequest);

    void delete(Long noticeBoardId);

    List<NoticeBoardListResponse> getBoardList(); /* 기본적으로 보여줄 게시글 목록 */

    NoticeBoardDetailResponse getBoardDetail(Long noticeBoardId); /* 글 선택 시 보여줄 상세 글 */

    List<NoticeBoardListResponse> searchBoardList(String query, String keyword); /* 작성일 순, 작성자가 포함된 글 불러오기 */
}

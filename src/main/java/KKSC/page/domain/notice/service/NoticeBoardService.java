package KKSC.page.domain.notice.service;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.dto.NoticeBoardResponse;
import KKSC.page.domain.notice.entity.NoticeBoard;

import java.util.List;

public interface NoticeBoardService {

    void create(Member member, NoticeBoardRequest noticeBoardRequest);

    void update(Member member, Long noticeBoardId, NoticeBoardRequest noticeBoardRequest);

    void delete(Member member, Long noticeBoardId);

    List<NoticeBoardResponse> getBoardList(); /* 기본적으로 보여줄 게시글 목록 */

    NoticeBoard getBoardDetail(Long noticeBoardId); /* 글 선택 시 보여줄 상세 글 */

    List<NoticeBoardResponse> searchBoardList(String cmd); /* 작성일 순, 작성자가 포함된 글 불러오기 */
}

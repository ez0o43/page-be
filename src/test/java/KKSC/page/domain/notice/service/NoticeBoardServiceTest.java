package KKSC.page.domain.notice.service;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.repository.MemberRepository;
import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.entity.Keyword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeBoardServiceTest {

    @Autowired NoticeBoardService noticeBoardService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 생성_성공() throws Exception {
        //given
        NoticeBoardRequest request = new NoticeBoardRequest("title", "content", 0L);
        Member member = Member.builder().username("Kim").build();

        //when
        memberRepository.save(member);
        noticeBoardService.create(request, member.getUsername());
        NoticeBoardDetailResponse board = noticeBoardService.getBoardDetail(1L);
        System.out.println(board);

        //then
        assertEquals("title", board.title());
    }

    /* 게시물 수정 테스트 케이스 */
    @Test
    void 게시글_수정시_수정됨_표시() throws Exception {
        //given
        NoticeBoardRequest boardRequest = new NoticeBoardRequest("title", "content", 0L);
        NoticeBoardRequest updateRequest = new NoticeBoardRequest("title-update", "content-update", 0L);

        //when
        noticeBoardService.create(boardRequest, "Kim"); // 새로운 공지 생성
        NoticeBoardDetailResponse board = noticeBoardService.update(1L, updateRequest);// 수정할 공지로 변경

        //then
        assertEquals("title-update", board.title());
        assertEquals("content-update", board.content());
        assertEquals(0L, board.delYN());
        assertNotNull(board.createdAt());
        assertNotNull(board.createdBy());
        assertNotNull(board.modifiedAt());
    }

    /* 게시물 삭제 테스트 케이스 */
    @Test
    void 삭제시_delYN_검증() throws Exception {
        //given
        NoticeBoardRequest boardRequest = new NoticeBoardRequest("title", "content", 0L);

        //when
        noticeBoardService.create(boardRequest, "Kim"); // 공지 생성
        noticeBoardService.delete(1L); // 공지 삭제

        NoticeBoardDetailResponse board = noticeBoardService.getBoardDetail(1L);

        //then
        assertEquals(1L, board.delYN()); // delYN: 0 -> 1
    }

    /* 게시물 조회 테스트 케이스 */
    @Test
    void 키워드_검색시_키워드포함_게시물만_조회() throws Exception {
        //given
        NoticeBoardRequest boardRequest = new NoticeBoardRequest("title", "content", 0L);
        NoticeBoardRequest secondRequest = new NoticeBoardRequest("hello", "world", 0L);

        //when
        noticeBoardService.create(boardRequest, "Kim");
        noticeBoardService.create(secondRequest, "Kim");
        List<NoticeBoardListResponse> listResponses = noticeBoardService.searchBoardList("title", Keyword.TITLE.name());

        //then
        assertEquals(1, listResponses.size()); // 2개가 입력됬어도 title이 포함된 글은 1개
        assertEquals("title", listResponses.get(0).title());
    }

    @Test
    void 키워드X_최신순_오래된순_조회순_조회() throws Exception {
        //given
        NoticeBoardRequest boardRequest = new NoticeBoardRequest("title", "content", 0L);
        NoticeBoardRequest boardRequest2 = new NoticeBoardRequest("title-2", "content", 0L);
        NoticeBoardRequest boardRequest3 = new NoticeBoardRequest("title-3", "content", 0L);

        //when
        noticeBoardService.create(boardRequest, "Kim");
        noticeBoardService.create(boardRequest2, "Kim");
        noticeBoardService.create(boardRequest3, "Kim");

        //then
    }

    @Test
    void 키워드_최신순_오래된순_조회순_조회() throws Exception {
        //given

        //when

        //then
    }

    // 조회수 증가 테스트코드 작성하기
    @Test
    void 조회수_증가_검증() throws Exception {
        //given

        //when

        //then
    }
}
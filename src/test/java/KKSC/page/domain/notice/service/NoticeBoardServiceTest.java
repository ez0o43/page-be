package KKSC.page.domain.notice.service;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeBoardServiceTest {

    @Autowired
    NoticeBoardService noticeBoardService;

    @Test
    void 생성_성공() throws Exception {
        //given
        NoticeBoardRequest request = new NoticeBoardRequest("title", "content", 0L);

        //when
        noticeBoardService.create(request);
        NoticeBoardDetailResponse board = noticeBoardService.getBoardDetail(1L);
        System.out.println(board.toString());

        //then
        assertEquals("title", board.title());
    }

    /* 게시물 작성 테스트 케이스 */
    @Test
    void 제목_내용_필수입력_실패() throws Exception {
        //given
        NoticeBoardRequest noneTitle = new NoticeBoardRequest(null, "content", 0L);
        NoticeBoardRequest noneContent = new NoticeBoardRequest("title", null, 0L);

        //when, then
        assertThrows(Exception.class, () -> noticeBoardService.create(noneTitle));
        assertThrows(Exception.class, () -> noticeBoardService.create(noneContent));
    }

    @Test
    void 제목_최대글자초과시_실패() throws Exception {
        //given
        NoticeBoardRequest exceedTitle = new NoticeBoardRequest("1234567891011121314151617181920",
                "content", 0L);

        //when, then
        assertThrows(Exception.class, () -> noticeBoardService.create(exceedTitle));
    }

    /* 게시물 수정 테스트 케이스 */
    @Test
    void 게시글_수정시_수정됨_표시() throws Exception {
        //given
        NoticeBoardRequest boardRequest = new NoticeBoardRequest("title", "content", 0L);
        NoticeBoardRequest updateRequest = new NoticeBoardRequest("title-update", "content-update", 0L);

        //when
        noticeBoardService.create(boardRequest); // 새로운 공지 생성
        noticeBoardService.update(1L, updateRequest); // 수정할 공지로 변경

        NoticeBoardDetailResponse board = noticeBoardService.getBoardDetail(1L);

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
        noticeBoardService.create(boardRequest); // 공지 생성
        noticeBoardService.delete(1L); // 공지 삭제

        NoticeBoardDetailResponse board = noticeBoardService.getBoardDetail(1L);

        //then
        assertEquals(0L, board.delYN()); // delYN: 1 -> 0
    }

    /* 게시물 조회 테스트 케이스 */
    @Test
    void 키워드_검색시_키워드포함_게시물만_조회() throws Exception {
        //given

        //when

        //then
    }

    @Test
    void 키워드X_최신순_오래된순_조회순_조회() throws Exception {
        //given

        //when

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
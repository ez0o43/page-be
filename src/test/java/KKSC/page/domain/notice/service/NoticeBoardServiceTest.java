package KKSC.page.domain.notice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeBoardServiceTest {

    @Autowired NoticeBoardService noticeBoardService;

    /* 게시물 작성 테스트 케이스 */
    @Test
    void 제목_내용_필수입력검사() throws Exception {
        //given

        //when

        //then
    }

    @Test
    void 제목_최대글자초과시_실패() throws Exception {
        //given

        //when

        //then
    }

    /* 게시물 수정 테스트 케이스 */
    @Test
    void 권한없을때_작성시_실패() throws Exception {
        //given

        //when

        //then
    }

    @Test
    void 게시글_수정시_수정됨_표시() throws Exception {
        //given

        //when

        //then
    }

    /* 게시물 삭제 테스트 케이스 */
    @Test
    void 권한없을때_삭제시_실패() throws Exception {
        //given

        //when

        //then
    }

    @Test
    void 삭제_확인메세지_출력확인() throws Exception {
        //given

        //when

        //then
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
}
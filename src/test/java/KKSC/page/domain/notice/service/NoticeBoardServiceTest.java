package KKSC.page.domain.notice.service;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.repository.MemberRepository;
import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.entity.NoticeBoard;
import KKSC.page.domain.notice.exeption.NoticeBoardException;
import KKSC.page.domain.notice.repository.NoticeBoardRepository;
import KKSC.page.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeBoardServiceTest {

    @Autowired
    NoticeBoardService noticeBoardService;
    @Autowired
    NoticeBoardRepository noticeBoardRepository;
    @Autowired
    MemberRepository memberRepository;

//    @Qualifier("memberService")
//    @Autowired
//    private MemberService memberService;

    /* 게시물 생성 테스트 케이스 */
    @Test
    void 게시글_생성_성공() throws Exception {
        //given
        NoticeBoardRequest request = new NoticeBoardRequest("title", "content", 0L);
        Member member = Member.builder().username("Kim").build();

        //when
        memberRepository.save(member);
        Long createdId = noticeBoardService.create(request, member.getUsername());
        NoticeBoardDetailResponse board = noticeBoardService.getBoardDetail(createdId); // 생성된 공지사항 조회

        //then
        assertThat(board.title()).isEqualTo("title");
    }

    /* 게시물 수정 테스트 케이스 */
    @Test
    void 게시글_수정시_수정됨_표시() throws Exception {
        //given
        NoticeBoardRequest boardRequest = new NoticeBoardRequest("title", "content", 0L);
        Long createdId = noticeBoardService.create(boardRequest, "Kim");           // 새로운 공지글 생성

        //when
        NoticeBoardRequest updateRequest = new NoticeBoardRequest("title-update", "content-update", 0L);
        NoticeBoardDetailResponse board = noticeBoardService.update(createdId, updateRequest); // 수정할 공지로 변경

        //then
        assertThat(board.title()).isEqualTo("title-update");
        assertThat(board.content()).isEqualTo("content-update");
        assertThat(board.delYN()).isEqualTo(0L);
        assertThat(board.createdAt()).isNotNull();
        assertThat(board.createdBy()).isNotNull();
        assertThat(board.modifiedAt()).isNotNull();
    }

    @Test
    public void 존재하지않는_게시글_수정() {
        //given
        Long noExistId = 101L; // 존재하지 않는 게시글
        NoticeBoardRequest updateRequest = new NoticeBoardRequest("title-update", "content-update", 0L);

        //when
        NoticeBoardException exception = assertThrows(NoticeBoardException.class, () -> {
            noticeBoardService.update(noExistId, updateRequest);
        });

        //then
        assertEquals(ErrorCode.NOT_FOUND_BOARD, exception.getErrorCode());
    }

    /* 게시물 삭제 테스트 케이스 */
    @Test
    void 삭제시_delYN_검증() throws Exception {
        //given
        NoticeBoardRequest boardRequest = new NoticeBoardRequest("title", "content", 0L);
        Long createdId = noticeBoardService.create(boardRequest, "Kim");    // 공지 생성

        //when
        noticeBoardService.delete(createdId); // 공지 삭제
        NoticeBoard board = noticeBoardRepository.findById(createdId)
                .orElseThrow(() -> new NoticeBoardException(ErrorCode.NOT_FOUND_BOARD));

        //then
        assertThat(board.getDelYN()).isEqualTo(1L); // delYN: 0 -> 1
    }

    @Test
    public void 이미_삭제된_게시물_다시_삭제() throws Exception {
        //given
        NoticeBoardRequest boardRequest = new NoticeBoardRequest("title", "content", 0L);
        Long createdId = noticeBoardService.create(boardRequest, "Kim");  // 공지 생성
        noticeBoardService.delete(createdId);  // 공지 삭제

        //when
        NoticeBoardException exception = assertThrows(NoticeBoardException.class, () -> {
            noticeBoardService.delete(createdId);
        });

        //then
        assertEquals(ErrorCode.ALREADY_DELETED, exception.getErrorCode());
    }

    /* 게시물 조회 테스트 케이스 */
    @Test
    public void 이미_삭제된_게시물_조회() throws Exception {
        //given
        NoticeBoardRequest boardRequest = new NoticeBoardRequest("title", "content", 0L);
        Long createdId = noticeBoardService.create(boardRequest, "Kim");  // 공지 생성
        noticeBoardService.delete(createdId);  // 공지 삭제

        //when
        NoticeBoardException exception = assertThrows(NoticeBoardException.class, () -> {
            noticeBoardService.getBoardDetail(createdId);
        });

        //then
        assertEquals(ErrorCode.ALREADY_DELETED, exception.getErrorCode());
    }

    @Test
    void 페이징_게시물_조회() throws Exception {
        //given
//        for (int i = 0; i < 100; ++i) {
//            NoticeBoardRequest boardRequest = new NoticeBoardRequest("title" + i, "content" + i, 0L);
//            noticeBoardService.create(boardRequest, "member" + i);
//        }
        PageRequest pageRequest1 = PageRequest.of(0, 10);
        PageRequest pageRequest2 = PageRequest.of(1, 10);
        PageRequest pageRequest3 = PageRequest.of(0, 20);

        //when
        Page<NoticeBoardListResponse> boardList1 = noticeBoardService.getBoardList(pageRequest1);
        Page<NoticeBoardListResponse> boardList2 = noticeBoardService.getBoardList(pageRequest2);
        Page<NoticeBoardListResponse> boardList3 = noticeBoardService.getBoardList(pageRequest3);

        //then
        assertThat(boardList1.getNumber()).isEqualTo(0);            // 현재 페이지(page)
        assertThat(boardList1.getNumberOfElements()).isEqualTo(10); // 페이지에 포함된 게시글 수 확인
        assertThat(boardList1.getTotalElements()).isEqualTo(10);    // 총 게시글 수 확인
        assertThat(boardList1.isFirst()).isTrue();                            // 현재 페이지 = 첫페이지

        assertThat(boardList2.getNumber()).isEqualTo(1);            // 현재 페이지(page)
        assertThat(boardList2.getNumberOfElements()).isEqualTo(10); // 페이지에 포함된 게시글 수 확인
        assertThat(boardList2.getTotalElements()).isEqualTo(20);    // 총 게시글 수 확인 : 2페이지니까 총 20개

        assertThat(boardList3.getNumber()).isEqualTo(0);            // 현재 페이지(page)
        assertThat(boardList3.getNumberOfElements()).isEqualTo(20); // 페이지에 포함된 게시글 수 확인 : pageSize는 20이다.
        assertThat(boardList3.getTotalElements()).isEqualTo(20);    // 총 게시글 수 확인
    }

    @Test
    void 키워드_검색시_키워드포함_게시물만_조회() throws Exception {
        //given
        NoticeBoardRequest boardRequest1 = new NoticeBoardRequest("제목1", "content", 0L);
        NoticeBoardRequest boardRequest2 = new NoticeBoardRequest("제목2", "content", 0L);
        NoticeBoardRequest secondRequest = new NoticeBoardRequest("hello", "world", 0L);
        PageRequest pageable = PageRequest.of(0, 10);
        noticeBoardService.create(boardRequest1, "Kim");
        noticeBoardService.create(boardRequest2, "Kim");
        noticeBoardService.create(secondRequest, "Kim");

        //when
        Page<NoticeBoardListResponse> listResponsePage = noticeBoardService.searchBoardList(Keyword.TITLE, "제목", pageable);
        List<NoticeBoardListResponse> listResponses = listResponsePage.getContent();

        //then
        assertThat(listResponses.size()).isEqualTo(2); // 2개가 입력됬어도 title이 포함된 글은 1개
        assertThat(listResponses.get(0).title()).isEqualTo("제목1"); // 검색 후 첫번째 게시글 제목
        assertThat(listResponses.get(1).title()).isEqualTo("제목2"); // 검색 후 두번째 게시글 제목
    }

    @Test
    void 키워드X_최신순_오래된순_조회순_조회() throws Exception {
        //given
//        NoticeBoardRequest boardRequest1 = new NoticeBoardRequest("제목1", "content", 0L);
//        NoticeBoardRequest boardRequest2 = new NoticeBoardRequest("제목2", "content", 0L);
//        NoticeBoardRequest boardRequest3 = new NoticeBoardRequest("제목3", "content", 0L);
//        noticeBoardService.create(boardRequest1, "Kim");
//        noticeBoardService.create(boardRequest2, "Kim");
//        noticeBoardService.create(boardRequest3, "Kim");
        PageRequest descRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt"); // 최신순
        PageRequest ascRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "createdAt");   // 오래된순
        PageRequest viewRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "view");      // 조회순

        //when
        Page<NoticeBoardListResponse> descList = noticeBoardService.getBoardList(descRequest);
        List<NoticeBoardListResponse> descResponses = descList.getContent();

        Page<NoticeBoardListResponse> ascList = noticeBoardService.getBoardList(ascRequest);
        List<NoticeBoardListResponse> ascResponses = ascList.getContent();

        Page<NoticeBoardListResponse> viewList = noticeBoardService.getBoardList(viewRequest);
        List<NoticeBoardListResponse> viewResponses = viewList.getContent();

        //then
        assertThat(descList).isNotNull();
        assertThat(ascList).isNotNull();

        /* 최신순 */
        assertThat(descResponses.get(0).title()).isEqualTo("title99");
        assertThat(descResponses.get(1).title()).isEqualTo("title98");
        assertThat(descResponses.get(2).title()).isEqualTo("title97");

        /* 오래된순 */
        assertThat(ascResponses.get(0).title()).isEqualTo("title0");
        assertThat(ascResponses.get(1).title()).isEqualTo("title1");
        assertThat(ascResponses.get(2).title()).isEqualTo("title2");

        /* 조회순 */
        assertThat(viewResponses.get(0).title()).isEqualTo("title99");
        assertThat(viewResponses.get(1).title()).isEqualTo("title98");
        assertThat(viewResponses.get(2).title()).isEqualTo("title97");
    }

    @Test
    void 키워드_최신순_오래된순_조회() throws Exception {
        //given
        NoticeBoardRequest boardRequest1 = new NoticeBoardRequest("제목1", "내용1", 0L);
        NoticeBoardRequest boardRequest2 = new NoticeBoardRequest("제목2", "내용2", 0L);
        NoticeBoardRequest boardRequest3 = new NoticeBoardRequest("제목3", "내용3", 0L);
        noticeBoardService.create(boardRequest1, "aaa");
        noticeBoardService.create(boardRequest2, "aab");
        noticeBoardService.create(boardRequest3, "abc");

        PageRequest ascRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt"); // 최신순
        PageRequest descRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "createdAt"); // 오래된순

        //when
        List<NoticeBoardListResponse> ascTitle = noticeBoardService.searchBoardList(Keyword.TITLE, "제목", ascRequest).getContent();
        List<NoticeBoardListResponse> descContent = noticeBoardService.searchBoardList(Keyword.CONTENT, "내용", descRequest).getContent();
        List<NoticeBoardListResponse> ascCreatedBy = noticeBoardService.searchBoardList(Keyword.CREATED_BY, "a", ascRequest).getContent();

        //then
        assertThat(ascTitle).isNotNull();
        assertThat(descContent).isNotNull();
        assertThat(ascCreatedBy).isNotNull();

        /* 키워드_제목 + 최신순 */
        assertThat(ascTitle.get(0).title()).isEqualTo("제목3");
        assertThat(ascTitle.get(1).title()).isEqualTo("제목2");
        assertThat(ascTitle.get(2).title()).isEqualTo("제목1");

        /* 키워드_내용 + 오래된순 - NoticeBoardListResponse에 content 필드가 없는 관계로 일단 title로 테스트 */
        assertThat(descContent.get(0).title()).isEqualTo("제목1");
        assertThat(descContent.get(1).title()).isEqualTo("제목2");
        assertThat(descContent.get(2).title()).isEqualTo("제목3");

        /* 키워드_작성자 + 최신순 */
        assertThat(ascCreatedBy.get(0).createdBy()).isEqualTo("abc");
        assertThat(ascCreatedBy.get(1).createdBy()).isEqualTo("aab");
        assertThat(ascCreatedBy.get(2).createdBy()).isEqualTo("aaa");
    }

    @Test
    void 키워드_조회순_조회() throws Exception {
        //given
        NoticeBoard noticeBoard1 = NoticeBoard.builder().title("중요!!").content("내용1").delYN(0L).view(1L).build();
        NoticeBoard noticeBoard2 = NoticeBoard.builder().title("중요한 공지입니다").content("내용2").delYN(0L).view(10L).build();
        NoticeBoard noticeBoard3 = NoticeBoard.builder().title("7월 29일 공지").content("내용3").delYN(0L).view(15L).build();
        noticeBoardRepository.save(noticeBoard1);
        noticeBoardRepository.save(noticeBoard2);
        noticeBoardRepository.save(noticeBoard3);

        PageRequest viewRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "view");

        // when
        Page<NoticeBoardListResponse> listResponse = noticeBoardService.searchBoardList(Keyword.TITLE, "공지", viewRequest);
        List<NoticeBoardListResponse> boardList = listResponse.getContent();

        //then
        assertNotNull(boardList);
        assertEquals(2, boardList.size(), "공지가 포함된 게시글 수 : 2");
        assertEquals(15L, boardList.get(0).view(),"공지가 포함된 첫번째 게시글의 조회수 : 15");

        String[] expectedTitles = {"7월 29일 공지", "중요한 공지입니다"};
        String[] actualTitles = new String[boardList.size()];
        for (int i = 0; i < boardList.size(); i++) {
            actualTitles[i] = boardList.get(i).title();
        }
        assertArrayEquals(expectedTitles, actualTitles);
    }

    // 조회수 증가 테스트코드 작성하기
    @Test
    void 조회수_증가_검증() throws Exception {
        //given
        Long detailId = 10L;

        //when
        noticeBoardService.countUpView(detailId);
        noticeBoardService.countUpView(detailId);
        noticeBoardService.countUpView(detailId);

        //then
        NoticeBoard board = noticeBoardRepository.findById(detailId)
                .orElseThrow(() -> new NoticeBoardException(ErrorCode.NOT_FOUND_BOARD));
        assertEquals(3, board.getView(), "10번 게시글 조회수 : 3");
    }
}
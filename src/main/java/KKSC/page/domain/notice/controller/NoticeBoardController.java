package KKSC.page.domain.notice.controller;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.entity.NoticeBoard;
import KKSC.page.domain.notice.repository.NoticeBoardRepository;
import KKSC.page.domain.notice.service.NoticeBoardService;
import KKSC.page.global.exception.dto.ResponseVO;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    // 게시글 작성
    @PostMapping("/")
    public ResponseVO<Long> noticeCreate(@RequestBody @Valid NoticeBoardRequest request) {
        Long createdId = noticeBoardService.create(request, null);// memberName은 이후 로그인 구현 후 추가

        return new ResponseVO<>(createdId);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseVO<NoticeBoardDetailResponse> noticeUpdate(@PathVariable("id") Long id, @RequestBody @Valid NoticeBoardRequest request) {
        NoticeBoardDetailResponse detailResponse = noticeBoardService.update(id, request);

        return new ResponseVO<>(detailResponse);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseVO<String> noticeDelete(@PathVariable("id") Long id) {
        noticeBoardService.delete(id);

        return new ResponseVO<>("Delete success");
    }

    // 게시글 목록 조회(페이징)
    @GetMapping("/list")
    public ResponseVO<Page<NoticeBoardListResponse>> noticeListPage(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NoticeBoardListResponse> listResponses = noticeBoardService.getBoardList(pageable);
        return new ResponseVO<>(listResponses);
    }

    // 게시글 조회
    @GetMapping("/{id}")
    public ResponseVO<NoticeBoardDetailResponse> notice(@PathVariable("id") Long id) {
        NoticeBoardDetailResponse detailResponse = noticeBoardService.getBoardDetail(id);

        return new ResponseVO<>(detailResponse);
    }

    // 특정 키워드로 검색
    @GetMapping("/search")
    public ResponseVO<Page<NoticeBoardListResponse>> searchBoards(
            @RequestParam(defaultValue = "TITLE") Keyword keyword,
            @RequestParam String query,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NoticeBoardListResponse> listResponses = noticeBoardService.searchBoardList(keyword, query, pageable);

        return new ResponseVO<>(listResponses);
    }


    // test code
    private final NoticeBoardRepository noticeBoardRepository;

    @GetMapping("/test/list")
    public Page<NoticeBoardDetailResponse> test(@PageableDefault Pageable pageable) {
        return noticeBoardRepository.findAll(pageable)
                .map(entity -> NoticeBoardDetailResponse.fromEntity(entity, null));
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; ++i) {
            NoticeBoard noticeBoard = NoticeBoard.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .memberName("member" + i)
                    .delYN(0L)
                    .view(0L)
                    .build();
            noticeBoardRepository.save(noticeBoard);
        }
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/detail/{id}")
    public ResponseVO<NoticeBoardDetailResponse> noticeDetail(@PathVariable("id") Long id,
                                                              HttpServletRequest request, HttpServletResponse response) {
        // 현재 로그인한 사용자 정보 가져오기 - 지금은 username을 가져왔는데 학번가져오는게 더 좋을 듯함
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = extractUsername(authentication);
        String newUsername = username.replaceAll("[@.]", "_");
        String cookieName = "noticeBoardView_" + newUsername;

        // 쿠키 값 가져오기
        String noticeBoardViewCookie = getCookieValue(request, cookieName);
        readNotice(id, cookieName, noticeBoardViewCookie, response);
        NoticeBoardDetailResponse detailResponse = noticeBoardService.getBoardDetail(id);
        return new ResponseVO<>(detailResponse);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    private void readNotice(Long noticeBoardId, String cookieName, String cookieValue, HttpServletResponse response) {
        String noticeBoardIdStr = "[" + noticeBoardId + "]";

        if (cookieValue == null) { // cookieValue 없으면 새로운 쿠키
            noticeBoardService.countUpView(noticeBoardId); // 조회수 +1
            Cookie newCookie = new Cookie(cookieName, noticeBoardIdStr);
            newCookie.setPath("/notice");
            newCookie.setMaxAge(60 * 60 * 24); // 수명: 24시간
            response.addCookie(newCookie);
        } else { // cookieValue 있으면 기존 쿠키 -> 기존 쿠키값에 조회한 게시글 id 추가
            if (!cookieValue.contains(noticeBoardIdStr)) {
                noticeBoardService.countUpView(noticeBoardId); // 조회수 +1
                String newValue = cookieValue + noticeBoardIdStr;
                Cookie oldCookie = new Cookie(cookieName, newValue);
                oldCookie.setPath("/notice");
                oldCookie.setMaxAge(60 * 60 * 24); // 수명: 24시간
                response.addCookie(oldCookie);
            }
        }
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }
}
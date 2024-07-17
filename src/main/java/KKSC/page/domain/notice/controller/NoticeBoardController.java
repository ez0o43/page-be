package KKSC.page.domain.notice.controller;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.global.exception.dto.ResponseVO;
import KKSC.page.domain.notice.service.NoticeBoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    // 게시글 목록 조회
    @GetMapping("/list")
    public ResponseVO<List<NoticeBoardListResponse>> noticeList(){
        List<NoticeBoardListResponse> listResponse = noticeBoardService.getBoardList();
        return new ResponseVO<>(listResponse);
    }

    // 게시글 조회
    @GetMapping("/{id}")
    public ResponseVO<NoticeBoardDetailResponse> notice(@PathVariable("id") Long id) {
        NoticeBoardDetailResponse detailResponse = noticeBoardService.getBoardDetail(id);
        return new ResponseVO<>(detailResponse);
    }

    // 게시글 작성
    @PostMapping("/")
    public ResponseVO<Long> noticeCreate(@RequestBody @Valid NoticeBoardRequest request) {
        Long createdId = noticeBoardService.create(request, null);// memberName은 이후 로그인 구현 후 추가
        return new ResponseVO<>(createdId);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseVO<NoticeBoardDetailResponse> noticeUpdate(@PathVariable("id") Long id,@RequestBody @Valid NoticeBoardRequest request){
        NoticeBoardDetailResponse detailResponse = noticeBoardService.update(id, request);
        return new ResponseVO<>(detailResponse);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseVO<String> noticeDelete(@PathVariable("id") Long id) {
        noticeBoardService.delete(id);
        return new ResponseVO<>("Delete success");
    }
    @GetMapping("/search")
    public ResponseVO<List<NoticeBoardListResponse>> searchBoards(@RequestParam Keyword keyword, @RequestParam String query) {
        List<NoticeBoardListResponse> listResponses = noticeBoardService.searchBoardList(keyword, query);

        return new ResponseVO<>(listResponses);
    }
}
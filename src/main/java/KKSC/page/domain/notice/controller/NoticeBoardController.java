package KKSC.page.domain.notice.controller;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.entity.NoticeBoard;
import KKSC.page.domain.notice.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    // 게시글 목록 조회
    @GetMapping("/notices")
    public ResponseEntity<List<NoticeBoardListResponse>> noticeList(){
        List<NoticeBoardListResponse> listResponse = noticeBoardService.getBoardList();
        return ResponseEntity.ok().body(listResponse);
    }

    // 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<NoticeBoardDetailResponse> notice(@PathVariable Long id) {
//        NoticeBoardDetailResponse response = new NoticeBoardDetailResponse("Hello", "Spring",
//                "admin", 1L, 0L, null, null, LocalDateTime.now(), LocalDateTime.now());
        NoticeBoardDetailResponse detailResponse = noticeBoardService.getBoardDetail(id);
        return ResponseEntity.ok().body(detailResponse);
    }

    // 게시글 작성
    @PostMapping("/notice")
    public ResponseEntity<NoticeBoard> noticeCreate(@RequestBody NoticeBoardRequest request){
        NoticeBoard noticeBoard = noticeBoardService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeBoard);
    }

    // 게시글 수정
    @PutMapping("/notice/{id}")
    public ResponseEntity<NoticeBoard> noticeUpdate(@PathVariable Long id,@RequestBody NoticeBoardRequest request){
        noticeBoardService.update(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/notice/{id}")
    public ResponseEntity<NoticeBoard> noticeDelete(@PathVariable Long id) {
        noticeBoardService.delete(id);
        return ResponseEntity.ok().build();
    }
}
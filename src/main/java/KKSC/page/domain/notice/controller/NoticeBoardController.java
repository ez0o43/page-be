package KKSC.page.domain.notice.controller;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.entity.NoticeBoard;
import KKSC.page.domain.notice.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    @GetMapping("/notice")
    public ResponseEntity<NoticeBoardDetailResponse> notice() {
        NoticeBoardDetailResponse response = new NoticeBoardDetailResponse("Hello", "Spring",
                "admin", 1L, 0L, null, null, LocalDateTime.now(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/notices")
    public ResponseEntity<NoticeBoardListResponse> noticeList(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/notice")
    public ResponseEntity<NoticeBoard> noticeSave(@RequestBody NoticeBoard noticeBoard){
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/notice")
    public ResponseEntity<NoticeBoard> noticeUpdate(@RequestBody NoticeBoard noticeBoard){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/notice/{id}")
    public ResponseEntity<NoticeBoard> noticeDelete(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
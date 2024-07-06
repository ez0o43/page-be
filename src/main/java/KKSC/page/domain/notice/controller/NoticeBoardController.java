package KKSC.page.domain.notice.controller;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

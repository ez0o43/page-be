package KKSC.page.domain.notice.controller;

import KKSC.page.domain.notice.dto.NoticeBoardResponse;
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
    public ResponseEntity<NoticeBoardResponse> notice() {
        NoticeBoardResponse response = new NoticeBoardResponse("Hello", "Spring",
                10L, 0L, 0L, LocalDateTime.now(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

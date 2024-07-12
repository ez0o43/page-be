package KKSC.page.domain.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import KKSC.page.domain.notice.service.NoticeFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/noticefile")
@RequiredArgsConstructor
@Slf4j
public class NoticeFileController {

    private final NoticeFileService noticeFileService;

    // 공지사항 첨부파일 업로드
    @PostMapping("/{noticeboardid}")
    public ResponseEntity<Object> NoticeFileupload(MultipartHttpServletRequest multipartHttpServletRequest,
                                                   @PathVariable Long noticeboardid) throws Exception {

        return ResponseEntity.ok().body(noticeFileService.uploadFile(multipartHttpServletRequest, noticeboardid));
    }

    // 공지사항 첨부파일 다운로드
    @GetMapping("/{noticeFileId}")
    public ResponseEntity<Object> NoticeFileDownload(@PathVariable Long noticeFileId) throws Exception {

        return noticeFileService.downloadFile(noticeFileId);
    }

    // 공지사항 첨부파일 삭제
    @DeleteMapping("/{noticeFileId}")
    public ResponseEntity<Object>  NoticeFileDelete(@PathVariable Long noticeFileId) throws Exception {

        return ResponseEntity.ok().body(noticeFileService.deleteFile(noticeFileId));
    }


}

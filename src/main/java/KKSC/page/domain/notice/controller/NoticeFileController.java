package KKSC.page.domain.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import KKSC.page.domain.notice.service.NoticeFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Slf4j
public class NoticeFileController {
    
    @Autowired
    NoticeFileService noticeFileService;
    

    // 공지사항 첨부파일 업로드
    @PostMapping("/{noticeboardid}")
    public ResponseEntity<Object> NoticeFileupload(MultipartHttpServletRequest multipartHttpServletRequest,
            @PathVariable Integer noticeboardid) throws Exception {

        return ResponseEntity.ok().body(noticeFileService.uploadFile(multipartHttpServletRequest, noticeboardid));
    }

    // 공지사항 첨부파일 다운로드
    @GetMapping("/{noticeFileId}")
    public ResponseEntity<Object> NoticeFileDownload(@PathVariable Integer noticeFileId) throws Exception {

        return ResponseEntity.ok().body(noticeFileService.downloadFile(noticeFileId));
    }

    // 공지사항 첨부파일 삭제
    @DeleteMapping("/{noticeFileId}")
    public ResponseEntity<Object>  NoticeFileDelete(@PathVariable Integer noticeFileId) throws Exception {

    return ResponseEntity.ok().body(noticeFileService.deleteFile(noticeFileId));
    }


}

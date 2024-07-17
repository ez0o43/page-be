package KKSC.page.domain.notice.service.impl;

import KKSC.page.domain.notice.entity.NoticeFile;
import KKSC.page.domain.notice.exeption.NoticeFileException;
import KKSC.page.domain.notice.repository.NoticeFileRepository;
import KKSC.page.domain.notice.service.NoticeFileService;
import KKSC.page.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeFileServiceImpl implements NoticeFileService {

    private final HttpServletResponse response;
    private final NoticeFileRepository noticeFileRepository;

    // 업로드 경로 지정
    @Value(value = "${fileUploadBaseUrl}")
    private String uploadPath;

    /**
     * 공지사항 파일 업로드
     * @param noticeBoardId : 파일업로드 하고자 하는 공지사항 게시물의 번호
     * @return 미정
     * @since 2024.07.06
     * @version 0.01
     * @throws IOException
     * @throws IllegalStateException
     */
    @Override
    public String uploadFile(MultipartHttpServletRequest multipartHttpServletRequest, Long noticeBoardId) throws Exception, IOException {
        /*
         * 경로,파일명,파일사이즈,파일타입 빌더패턴으로 묶어서 정보 DB에 저장 후 서버에 파일 업로드
         */
        log.info("uploadPath = {}", uploadPath);

        File Folder = new File(uploadPath);
        // 폴더없을경우 생성 
        if (!Folder.exists()) {
            Folder.mkdir(); // 디렉터리 생성.
        }
        // 총 업로드 요청한 파일 갯수
        int total = 0;
        // 업로드 된 파일 갯수
        int cnt = 0;

        // 전체 NULL 체크
        if (!ObjectUtils.isEmpty(multipartHttpServletRequest)) {

            Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
            String name;

            while (iterator.hasNext()) {
                total++;
                name = iterator.next();
                
                List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
                for (MultipartFile multipartFile : list) {

                    if (!(multipartFile.getOriginalFilename() == null
                            || multipartFile.getOriginalFilename().isEmpty())) {
                        // File명 uuid 생성
                        String noticeFileNameUuid = multipartFile.getOriginalFilename() + UUID.randomUUID();
                        // File Baseurl 설정 
                        String completeuploadPath = uploadPath + "/" + noticeFileNameUuid;
                        // DB에 저장
                        NoticeFile noticeFile = NoticeFile.builder()
                                .noticeFileNameUuid(noticeFileNameUuid)
                                .noticeFileBaseUrl(completeuploadPath)
                                .noticeFileName(multipartFile.getOriginalFilename())
                                .noticeFileType(multipartFile.getContentType())
                                .noticeFileSize(multipartFile.getSize())
                                .build();

                        noticeFileRepository.save(noticeFile);
                        
                        cnt++;
                        // 실제 파일 업로드 
                        File uploadFile = new File(completeuploadPath);
                        multipartFile.transferTo(uploadFile);
                    } else // 앞선 파일이 조건에 걸려 안 걸어가면 뒤 파일을 올려야 함
                        continue;
                }
            }
        } else {
            throw new NoticeFileException(ErrorCode.NOT_FOUND_FILE);
        }

        return total + " 중 " + cnt + " 성공 ";
    }

    /**
     * 공지사항 파일 다운로드
     * @param noticeFileId : 다운로드 하고자 하는 파일의 번호
     * @return 파일
     * @since 2024.07.06
     * @version 0.01
     */
    @Override
    public Resource downloadFile(Long noticeFileId) {
        NoticeFile noticeFile = noticeFileRepository.findById(noticeFileId)
                .orElseThrow(() -> new NoticeFileException(ErrorCode.NOT_FOUND_FILE));
        String path = noticeFile.getNoticeFileBaseUrl();

        try {
            // 파일 경로 지정
            Path filePath = Paths.get(noticeFile.getNoticeFileBaseUrl());
            // 파일 불러오기 
            Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
            File file = new File(path);

            // 파일 이름 지정
            String filename = URLEncoder.encode(noticeFile.getNoticeFileName(), StandardCharsets.UTF_8);
            // 헤더 설정
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + filename + "\"");
            return resource;
        } catch (Exception e) {
            throw new NoticeFileException(ErrorCode.NOT_FOUND_FILE);
        }
    }

    /**
     * 공지사항 파일 삭제
     * @param noticeFileId : 삭제 하고자 하는 파일의 번호
     * @return 미정
     * @since 2024.07.06
     * @version 0.01
     */
    @Override
    public String deleteFile(Long noticeFileId) {
        /*
         * 파일아이디로 DB에서 파일 경로 찾아서 삭제 후 DB에서도 해당 데이터 삭제
         */
        NoticeFile noticeFile = noticeFileRepository.findById(noticeFileId)
                .orElseThrow(() -> new NoticeFileException(ErrorCode.NOT_FOUND_FILE));

        // 파일 지정
        File file = new File(noticeFile.getNoticeFileBaseUrl());

        // 파일 삭제
        file.delete();
        noticeFileRepository.deleteById(noticeFileId);

        return "파일 삭제 성공";
    }
}

package KKSC.page.domain.notice.service.impl;

import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.core.io.Resource;

import KKSC.page.domain.notice.entity.NoticeFile;
import KKSC.page.domain.notice.repository.NoticeFileRepository;
import KKSC.page.domain.notice.service.NoticeFileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeFileServiceImpl implements NoticeFileService {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    HttpServletResponse response;

    private final NoticeFileRepository noticeFileRepository;

    // 업로드 경로 지정 (경로 수정 예정 / 경로 수정 해야 테스트 가능 / 환경변수에 넣을 예정 )
    // 절대 경로
    private final String uploadPath = Paths.get("/Users/ijunhyeong/kkscve/page-be/src/main/resources/noticefile").toString();
    private final Path directoryPath = Paths.get("src", "main", "resources","noticefile");
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
         * 
         */
        File Folder = new File(uploadPath.toString());
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
                        String noticeFileNameUuid = multipartFile.getOriginalFilename()+UUID.randomUUID().toString();
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
                    } else
                        continue;
                }
            }
        } else {
            return "UPLOADFILEISNULL";
        }

        return Integer.toString(total) + " 중 " + Integer.toString(cnt) + " 성공 ";
    }

    /**
     * 공지사항 파일 다운로드
     * @param noticeFileId : 다운로드 하고자 하는 파일의 번호
     * @return 파일
     * @since 2024.07.06
     * @version 0.01
     */
    @Override
    public ResponseEntity<Object> downloadFile(Long noticeFileId) {
        Optional<NoticeFile> noticeFile = noticeFileRepository.findById(noticeFileId);
        String path = noticeFile.get().getNoticeFileBaseUrl();

        try {
            // 파일 경로 지정
            Path filePath = Paths.get(noticeFile.get().getNoticeFileBaseUrl());
            // 파일 불러오기 
            Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
            File file = new File(path);

            // 파일 이름 지정
            String filename = URLEncoder.encode(noticeFile.get().getNoticeFileName(), "UTF-8");
            // 헤더 설정
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + filename + "\"");

            return new ResponseEntity<Object>(resource, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
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
        Optional<NoticeFile> noticeFile = noticeFileRepository.findById(noticeFileId);
        // 파일 지정
        File file = new File(noticeFile.get().getNoticeFileBaseUrl());
        if( file.exists() ){
            // file 삭제 
            if(file.delete()){
                // DB 데이터 삭제 
                noticeFileRepository.deleteById(noticeFileId);
            }else{
                log.info("error");
            }
        }else{
            log.info("데이터 없음");
        }
        return null;
    }
}

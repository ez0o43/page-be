package KKSC.page.domain.notice.service.impl;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import KKSC.page.domain.notice.service.NoticeFileService;
import jakarta.servlet.http.HttpServletResponse;

public class NoticeFileServiceImpl implements NoticeFileService {

    
    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    HttpServletResponse response;

    // 업로드 경로 지정 
    private final String uploadPath = Paths.get("C:", "develop", "upload-files").toString();

    /**
	 * 공지사항 파일 업로드 
	 * @param noticeBoardId : 파일업로드 하고자 하는 공지사항 게시물의 번호
	 * @return 미정
     * @since 2024.07.06     
     * @version 0.01
	 */
    @Override
    public String uploadFile(MultipartHttpServletRequest multipartHttpServletRequest, int noticeBoardId) {
        /*
         * 경로,파일명,파일사이즈,파일타입 빌더패턴으로 묶어서 정보 DB에 저장 후 서버에 파일 업로드
         */
        return null;
    }

    /**
	 * 공지사항 파일 다운로드 
	 * @param noticeFileId : 다운로드 하고자 하는 파일의 번호
	 * @return 미정
     * @since 2024.07.06
     * @version 0.01
	 */
    @Override
    public String downloadFile(Integer noticeFileId) {
        /*
         * 파일아이디로 DB에서 파일 경로 찾아서 서버에서 파일 다운로드 
         */
        return null;
    }

    /**
	 * 공지사항 파일 삭제 
	 * @param noticeFileId : 삭제 하고자 하는 파일의 번호
	 * @return 미정
     * @since 2024.07.06
     * @version 0.01
	 */
    @Override
    public String deleteFile(Integer noticeFileId) {
        /*
         * 파일아이디로 DB에서 파일 경로 찾아서 삭제 후 DB에서도 해당 데이터 삭제
         */
        return null;
    }


}

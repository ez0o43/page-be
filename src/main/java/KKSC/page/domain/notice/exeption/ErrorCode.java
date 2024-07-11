package KKSC.page.domain.notice.exeption;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /* 사용자 */
    NOT_FOUND_MEMBER(00, "사용자를 찾을 수 없습니다."),

    /* 게시판 & 공지 */
    NOT_FOUND_TITLE(00, "제목을 입력하세요."),
    NOT_FOUND_CONTENT(00, "내용을 입력하세요."),
    TITLE_MAX_NUMBER(00, "최대 글자 수를 초과했습니다."),
    NOT_FOUND_BOARD(00, "게시물을 찾을 수 없습니다."),

    /* 파일 */
    FILE_OVERSIZE(00, "파일 용량을 초과했습니다."),

    /* 캘린더 */

    /* 공용 */
    NOT_FOUND_ACCESS_TOKEN(00, "토큰을 찾을 수 없습니다."),
    ;

    private final int errorCode;
    private final String message;
}

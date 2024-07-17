package KKSC.page.global.exception;

import KKSC.page.domain.notice.exeption.NoticeBoardException;
import KKSC.page.domain.notice.exeption.NoticeFileException;
import KKSC.page.global.exception.dto.ErrorResponseVO;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoticeBoardException.class)
    public ErrorResponseVO handleNoticeBoardException(NoticeBoardException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return getErrorResponse(errorCode);
    }

    @ExceptionHandler(NoticeFileException.class)
    public ErrorResponseVO handleNoticeFileException(NoticeFileException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return getErrorResponse(errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseVO handleValidationException(MethodArgumentNotValidException ex) {
        return ErrorResponseVO.builder()
                .name("VALIDATION_ERROR")
                .errorCode(ex.getStatusCode().value())
                .message("정확한 규격에 맞춰 입력하세요.").build();
    }

    private ErrorResponseVO getErrorResponse(ErrorCode errorCode) {
        return ErrorResponseVO.builder()
                .name(errorCode.name())
                .errorCode(errorCode.getErrorCode())
                .message(errorCode.getMessage()).build();
    }
}

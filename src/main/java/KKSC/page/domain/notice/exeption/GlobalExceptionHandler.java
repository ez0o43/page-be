package KKSC.page.domain.notice.exeption;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoticeBoardException.class)
    public ErrorResponseVO handleCustomException(NoticeBoardException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return getErrorResponse(errorCode);
    }

    private ErrorResponseVO getErrorResponse(ErrorCode errorCode) {
        return ErrorResponseVO.builder()
                .name(errorCode.name())
                .errorCode(errorCode.getErrorCode())
                .message(errorCode.getMessage()).build();
    }
}

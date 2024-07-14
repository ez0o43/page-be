package KKSC.page.domain.notice.exeption;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoticeFileException extends RuntimeException {

    private final ErrorCode errorCode;
}

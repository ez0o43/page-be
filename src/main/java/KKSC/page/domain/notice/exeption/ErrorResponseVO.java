package KKSC.page.domain.notice.exeption;

public record ErrorResponseVO (
        int errorCode,
        String message
) {}

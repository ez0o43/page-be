package KKSC.page.domain.notice.exeption;

import lombok.Builder;

@Builder
public record ErrorResponseVO (
        String name,
        int errorCode,
        String message
) {}

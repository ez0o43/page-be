package KKSC.page.domain.notice.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record NoticePageResponse<T> (
        int totalPages,
        long totalElements,
        int currentPage,
        List<T> content
) {
    public NoticePageResponse(Page<T> page) {
        this(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getContent()
        );
    }
}

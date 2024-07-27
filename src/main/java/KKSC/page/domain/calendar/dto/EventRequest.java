package KKSC.page.domain.calendar.dto;

import KKSC.page.domain.calendar.entity.Category;

import java.time.LocalDateTime;

public record EventRequest(
        String title,
        String detail,
        Category category,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long maxParticipant) {

    public EventRequest toEntity() {
        return new EventRequest(title, detail, category, startDate, endDate, maxParticipant);
    }
}

package KKSC.page.domain.calendar.dto;

import KKSC.page.domain.calendar.entity.Calendar;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record CalendarRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDate,
        String detail) {

    public Calendar toEntity() {
        return Calendar.builder()
                .startDate(startDate)
                .endDate(endDate)
                .detail(detail)
                .build();
    }
}

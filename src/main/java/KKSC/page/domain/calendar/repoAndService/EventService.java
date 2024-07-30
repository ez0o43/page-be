package KKSC.page.domain.calendar.repoAndService;

import KKSC.page.domain.calendar.dto.EventRequest;
import KKSC.page.domain.calendar.dto.EventResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface EventService {
    
    // 일정 목록 조회
    List<EventResponse> getScheduleList(Long year, Long month);

    // 일정 생성
    Long createSchedule(EventRequest eventRequest);

    // 일정 삭제
    void deleteSchedule(Long id);

    // 일정 수정
    EventResponse updateSchedule(Long id, EventRequest eventRequest);

    // 일정 참가
    Long joinSchedule(Long id, String name);

    // 일정 참가 취소
    void cancelSchedule(Long id);

}

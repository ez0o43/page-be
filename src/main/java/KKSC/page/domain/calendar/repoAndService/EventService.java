package KKSC.page.domain.calendar.repoAndService;

import org.springframework.stereotype.Service;


@Service
public interface EventService {
    
    // 일정 목록 조회    
    Long getScheduleList();

    // 일정 생성
    Long createSchedule();

    // 일정 삭제
    Long deleteSchedule();

    // 일정 수정
    Long updateSchedule();

    // 일정 참가
    Long joinSchedule();

    // 일정 참가 취소
    Long cancelSchedule ();

}

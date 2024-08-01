package KKSC.page.domain.calendar.repoAndService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
public interface EventService {
    
    // 일정 목록 조회
    @PreAuthorize("hasRole('permission_level1 ')")
    Long getScheduleList();

    // 일정 생성
    @PreAuthorize("hasRole('permission_level0 ')")
    Long createSchedule();

    // 일정 삭제
    @PreAuthorize("hasRole('permission_level0 ')")
    Long deleteSchedule();

    // 일정 수정
    @PreAuthorize("hasRole('permission_level0 ')")
    Long updateSchedule();

    // 일정 참가
    @PreAuthorize("hasRole('permission_level1 ')")
    Long joinSchedule();

    // 일정 참가 취소
    @PreAuthorize("hasRole('permission_level1 ')")
    Long cancelSchedule ();

}

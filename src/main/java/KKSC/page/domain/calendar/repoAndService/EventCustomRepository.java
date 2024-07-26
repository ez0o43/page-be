package KKSC.page.domain.calendar.repoAndService;

import org.springframework.data.jpa.repository.JpaRepository;

import KKSC.page.domain.calendar.entity.Event;


public interface EventCustomRepository extends JpaRepository<Event, Long>  {
    
    // 월 별로 스케줄 가져오기 
    void getScheduleList();
    
}

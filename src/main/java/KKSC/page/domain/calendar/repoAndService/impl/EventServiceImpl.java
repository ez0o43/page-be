package KKSC.page.domain.calendar.repoAndService.impl;

import org.springframework.stereotype.Service;

import KKSC.page.domain.calendar.repoAndService.EventCustomRepository;
import KKSC.page.domain.calendar.repoAndService.EventRepository;
import KKSC.page.domain.calendar.repoAndService.EventService;
import KKSC.page.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    
    private final EventRepository eventRepository;
    private final EventCustomRepository eventCustomRepository;
    
    // 일정 목록 조회    
    // 파라미터 : 몇년 몇월 받아오기
    @Override
    public Long getScheduleList(){
        eventCustomRepository.getScheduleList();
        return null;
    };

    
    // 일정 생성
    // 파라미터 : 스케쥴 생성 값 
    @Override
    public Long createSchedule(){
        eventRepository.save(null);
        return null;
    };
    
    // 일정 삭제
    // 파라미터 : 스케줄 아이디
    @Override
    public Long deleteSchedule(){
        eventRepository.deleteAllById(null);
        return null;
    };
        
    
    // 일정 수정
    // 파라미터 : 스케줄 수정 값
    @Override
    public Long updateSchedule(){
        eventRepository.save(null);
        return null;
    };
    
    // 일정 참가
    // 파라미터 : 스케줄 아이디, 유저아이디
    @Override
    public Long joinSchedule(){
        eventRepository.save(null);
        return null;
    };
    
    // 일정 참가 취소
    // 파라미터 : 스케줄 아이디, 유저아이디
    @Override
    public Long cancleSchedule (){
        eventRepository.delete(null);
        return null;
    };

}

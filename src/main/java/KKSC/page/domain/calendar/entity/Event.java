package KKSC.page.domain.calendar.entity;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long id;

    @OneToMany(mappedBy = "event")
    private List<Participant> participants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private Long maxParticipant;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String detail;

//    private void update(CalendarRequest calendarrequest){
//        this.startDate = calendarrequest.startDate();
//        this.endDate = calendarrequest.endDate();
//        this.detail = calendarrequest.detail();
//    }
}

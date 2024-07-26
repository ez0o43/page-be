package KKSC.page.domain.calendar.entity;

import KKSC.page.domain.calendar.dto.CalendarRequest;
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
public class Calendar extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long id;

    private String title;

    @Enumerated(value = EnumType.STRING)
    private Category category;

//    @OneToMany(mappedBy = "participants")
//    private List<Participant> participants;

    private Long numberPeople;
    private void addPerson(){
        numberPeople++;
    }

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String detail;

    private void update(CalendarRequest calendarrequest){
        this.startDate = calendarrequest.startDate();
        this.endDate = calendarrequest.endDate();
        this.detail = calendarrequest.detail();
    }
}

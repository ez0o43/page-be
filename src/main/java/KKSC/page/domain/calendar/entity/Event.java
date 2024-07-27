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
    @Column(name = "event_id")
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

    public void update(String title, String detail, Category category, LocalDateTime startDate, LocalDateTime endDate, Long maxParticipant) {
        this.title = title;
        this.detail = detail;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxParticipant = maxParticipant;
    }
}

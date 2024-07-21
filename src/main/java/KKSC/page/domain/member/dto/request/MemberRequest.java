package KKSC.page.domain.member.dto.request;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.entity.Profile;

public record MemberRequest(
        String email,
        String password,
        String username,
        String studentId,
        String intro,
        String nickname
) {
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .username(username)
                .studentId(studentId)
                .build();
    }
}

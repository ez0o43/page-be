package KKSC.page.domain.member.dto;

import KKSC.page.domain.member.entity.Member;

public record MemberRequest(
        String email,
        String password,
        String username,
        String studentId,
        String intro,
        String nickname
) {
//    public Member toEntity(MemberRequest memberRequest) {
//        return Member.builder()
//                .email(memberRequest.email())
//                .password(memberRequest.password())
//                .username(memberRequest.username())
//                .studentId(memberRequest.studentId())
//                .intro(memberRequest.intro())
//                .nickname(memberRequest.nickname())
//                .build();
//    }
}

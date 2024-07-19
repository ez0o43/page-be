package KKSC.page.domain.member.dto;

public record MemberRequest(String email,
        String password,
        String username,
        String studentId,
        String intro,
        String nickname
) {}

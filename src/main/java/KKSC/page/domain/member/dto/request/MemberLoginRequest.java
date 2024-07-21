package KKSC.page.domain.member.dto.request;

public record MemberLoginRequest(
        String email,
        String password
) {}

package KKSC.page.domain.member.dto.request;

public record MemberRequest(
        String email,
        String password,
        String username,
        String studentId,
        String intro,
        String nickname

        /**
         * toEntity 추가 예정
         */
) {}

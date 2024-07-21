package KKSC.page.domain.member.dto.request;
import KKSC.page.domain.member.entity.Permission;

public record MemberRequest(
        String email,
        String password,
        String username,
        String studentId,
        String intro,
        String nickname,
        Permission permission
) {
    /**
     * toEntity 추가 예정
     */
}

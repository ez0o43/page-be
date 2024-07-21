package KKSC.page.domain.member.dto.response;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.entity.Permission;

public record MemberResponse(
        Long id,
        String email,
        String username,
        String studentId,
        String intro,
        String nickname,
        Permission permission
) {
    /**
     * fromEntity 추가예정
     */
}

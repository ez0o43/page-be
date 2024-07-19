package KKSC.page.domain.member.service;

import KKSC.page.domain.member.dto.request.MemberRequest;

public interface MemberService {

    //회원가입
    void register(MemberRequest memberRequest);

    //회원탈퇴
    void retire(String email);

    //프로필 수정 (비밀번호 수정, 권한 관리, 프로필 수정)
    void update(MemberRequest memberRequest);
}

package KKSC.page.domain.member.service;

import KKSC.page.domain.member.dto.request.MemberLoginRequest;
import KKSC.page.domain.member.dto.request.MemberRequest;
import KKSC.page.domain.member.dto.response.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

public interface MemberService {

    //회원가입
    Long register(MemberRequest memberRequest);

    //로그인
    String login(MemberLoginRequest memberLoginRequest, HttpServletResponse response);

    //회원탈퇴
    void retire(String email);

    //프로필 수정 (비밀번호 수정, 권한 관리, 프로필 수정)
    void update(MemberRequest memberRequest);

    //프로필 조회
    MemberResponse getMemberProfile(String email);

    //로그아웃
    void logout(HttpServletRequest request);
}

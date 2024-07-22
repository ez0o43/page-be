package KKSC.page.domain.member.controller;

import KKSC.page.domain.member.dto.request.MemberLoginRequest;
import KKSC.page.domain.member.dto.request.MemberRequest;
import KKSC.page.domain.member.dto.response.MemberResponse;
import KKSC.page.domain.member.exception.MemberException;
import KKSC.page.domain.member.service.MemberService;
import KKSC.page.global.auth.service.JwtService;
import KKSC.page.global.exception.ErrorCode;
import KKSC.page.global.exception.dto.ResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;

    // 회원가입
    @PostMapping("/")
    public ResponseVO<String> register(@RequestBody @Valid MemberRequest request) {
        Long createdId = memberService.register(request);
        return new ResponseVO<>("가입 완료. 사용자 아이디 : " + createdId);
    }

    @PostMapping("/login")
    public ResponseVO<String> login(@RequestBody @Valid MemberLoginRequest memberLoginRequest,
                                    HttpServletResponse response) {
        String accessToken = memberService.login(memberLoginRequest, response);

        log.info("accessToken = {}", accessToken);
        return new ResponseVO<>(accessToken);
    }

    // 회원탈퇴
    @DeleteMapping("/")
    public ResponseVO<String> retire(@PathVariable String email) {
        memberService.retire(email);
        return new ResponseVO<>("탈퇴");
    }

    // 회원정보 수정
    @PutMapping("/")
    public ResponseVO<String> update(@RequestBody @Valid MemberRequest request) {
        memberService.update(request);
        return new ResponseVO<>("수정 완료");
    }

    // 회원 프로필 조회
    @GetMapping("/")
    public ResponseVO<MemberResponse> getMemberProfile(HttpServletRequest request) {
        String email = jwtService.extractUsername(request)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));

        log.info("accessToken = {}", request.getHeader("Authorization"));

        MemberResponse response = memberService.getMemberProfile(email);
        return new ResponseVO<>(response);
    }

    @GetMapping("/test")
    public String test(Authentication authentication) {
        String email = authentication.getName();
        MemberResponse member = memberService.getMemberProfile(email);

        return member.toString();
    }
}
package KKSC.page.domain.member.controller;
import KKSC.page.domain.member.dto.request.MemberRequest;
import KKSC.page.domain.member.dto.response.MemberResponse;
import KKSC.page.domain.member.service.MemberService;
import KKSC.page.global.exception.dto.ResponseVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/member")
    public ResponseVO<String> register(@RequestBody @Valid MemberRequest request) {
        memberService.register(request);
        return new ResponseVO<>("가입 완료");
    }

    // 회원탈퇴
    @DeleteMapping("/member")
    public ResponseVO<String> retire(@PathVariable String email) {
        memberService.retire(email);
        return new ResponseVO<>("탈퇴");
    }

    // 회원정보 수정
    @PutMapping("/member")
    public ResponseVO<String> update(@RequestBody @Valid MemberRequest request) {
        memberService.update(request);
        return new ResponseVO<>("수정 완료");
    }

    // 회원 프로필 조회
    @GetMapping("/{email}")
    public ResponseVO<MemberResponse> getMemberProfile(@PathVariable String email) {
        MemberResponse response = memberService.getMemberProfile(email);
        return new ResponseVO<>(response);
    }
}

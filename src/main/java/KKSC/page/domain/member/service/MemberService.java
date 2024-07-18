package KKSC.page.domain.member.service;

import KKSC.page.domain.member.dto.MemberRequest;

public interface MemberService {

    void register(MemberRequest memberRequest);

    void retire(String email);

    void update(MemberRequest memberRequest);
}

package KKSC.page.domain.member.service.impl;

import KKSC.page.domain.member.dto.request.MemberRequest;
import KKSC.page.domain.member.dto.response.MemberResponse;
import KKSC.page.domain.member.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Override
    public void register(MemberRequest memberRequest) {

    }

    @Override
    public void retire(String email) {

    }

    @Override
    public void update(MemberRequest memberRequest) {

    }

    @Override
    public MemberResponse getMemberProfile(String email) {
        return null;
    }
}

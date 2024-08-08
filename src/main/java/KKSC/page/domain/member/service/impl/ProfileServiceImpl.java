package KKSC.page.domain.member.service.impl;

import KKSC.page.domain.member.dto.request.ProfileRequest;
import KKSC.page.domain.member.dto.response.ProfileResponse;
import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.entity.Profile;
import KKSC.page.domain.member.exception.MemberException;
import KKSC.page.domain.member.repository.ProfileRepository;
import KKSC.page.domain.member.service.ProfileService;
import KKSC.page.domain.member.repository.MemberRepository;
import KKSC.page.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;

    @Override
    public ProfileResponse updateProfilePhoto(String email, ProfileRequest profileRequest) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));

        Profile profile = member.getProfile();
        if (profile == null) {
            throw new MemberException(ErrorCode.NOT_FOUND_PROFILE);
        }

        profile.updateProfile(profileRequest);
        profileRepository.save(profile);

        return ProfileResponse.from(profile);
    }

    @Override
    public boolean deleteProfilePhoto(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));

        Profile profile = member.getProfile();
        if (profile == null || profile.getProfilePhotoPath() == null) {
            return false;
        }

        profile.setProfilePhotoPath(null);
        profileRepository.save(profile);

        return true;
    }

    @Override
    public String getProfilePhotoPath(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));

        Profile profile = member.getProfile();
        if (profile == null) {
            throw new MemberException(ErrorCode.NOT_FOUND_PROFILE);
        }

        return profile.getProfilePhotoPath();
    }

    @Override
    public String getIntroductionByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));

        Profile profile = member.getProfile();
        if (profile == null) {
            throw new MemberException(ErrorCode.NOT_FOUND_PROFILE);
        }

        return profile.getIntro();
    }

    @Override
    public boolean deleteIntroduction(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));

        Profile profile = member.getProfile();
        if (profile == null || profile.getIntro() == null) {
            return false; // No introduction to delete
        }

        profile.setIntro(null);
        profileRepository.save(profile);

        return true;
    }
}

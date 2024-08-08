package KKSC.page.domain.member.service;

import KKSC.page.domain.member.dto.request.ProfileRequest;
import KKSC.page.domain.member.dto.response.ProfileResponse;
import KKSC.page.domain.member.entity.Member;

public interface ProfileService {
    ProfileResponse updateProfilePhoto(String email, ProfileRequest profileRequest);
    boolean deleteProfilePhoto(String email);
    String getProfilePhotoPath(String email);
    String getIntroductionByEmail(String email);
    boolean deleteIntroduction(String email);
}

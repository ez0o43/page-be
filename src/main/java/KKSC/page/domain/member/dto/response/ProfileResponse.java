package KKSC.page.domain.member.dto.response;

import KKSC.page.domain.member.entity.Profile;
import lombok.Getter;

@Getter
public class ProfileResponse {

    private final String intro;
    private final String nickname;
    private final String profilePhotoPath;

    private ProfileResponse(String intro, String nickname, String profilePhotoPath) {
        this.intro = intro;
        this.nickname = nickname;
        this.profilePhotoPath = profilePhotoPath;
    }

    public static ProfileResponse from(Profile profile) {
        return new ProfileResponse(
                profile.getIntro(),
                profile.getNickname(),
                profile.getProfilePhotoPath()
        );
    }
}

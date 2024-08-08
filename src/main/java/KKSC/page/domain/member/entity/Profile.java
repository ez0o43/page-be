package KKSC.page.domain.member.entity;

import KKSC.page.domain.member.dto.request.ProfileRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(of = {"id", "intro", "nickname", "profilePhotoPath"})

public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(mappedBy = "profile")
    private Member member;

    @Column(name = "intro", length = 200)
    private String intro;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "profile_photo_path", length = 255 )
    private String profilePhotoPath;

    public void setIntro(String introduction) {
        this.intro = introduction;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public void setMember(Member member) {
        this.member = member;
        if (member != null && member.getProfile() != this) {
            member.setProfile(this);
        }
    }

    public void updateProfile(ProfileRequest profileRequest) {
        this.intro = profileRequest.intro();
        this.nickname = profileRequest.nickname();
        this.profilePhotoPath = profileRequest.profilePhotoPath();
    }

}

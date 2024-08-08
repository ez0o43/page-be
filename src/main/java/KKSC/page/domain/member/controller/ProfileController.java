package KKSC.page.domain.member.controller;

import KKSC.page.domain.member.dto.request.ProfileRequest;
import KKSC.page.domain.member.dto.response.ProfileResponse;
import KKSC.page.domain.member.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // 프로필 사진 수정
    @PutMapping("/{email}/photo")
    public ResponseEntity<ProfileResponse> updatePhoto(@PathVariable String email, @RequestBody ProfileRequest profileRequest) {
        ProfileResponse updatedProfile = profileService.updateProfilePhoto(email, profileRequest);
        return ResponseEntity.ok(updatedProfile);
    }

    // 프로필 사진 삭제
    @DeleteMapping("/{email}/photo")
    public ResponseEntity<Void> deletePhoto(@PathVariable String email) {
        if (profileService.deleteProfilePhoto(email)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // 프로필 사진 조회
    @GetMapping("/{email}/photo")
    public ResponseEntity<String> getPhoto(@PathVariable String email) {
        String photoPath = profileService.getProfilePhotoPath(email);
        return photoPath != null ? ResponseEntity.ok(photoPath) : ResponseEntity.notFound().build();
    }

    // 소개글 조희
    @GetMapping("/{email}")
    public ResponseEntity<String> getIntro(@PathVariable String email) {
        String intro = profileService.getIntroductionByEmail(email);
        return intro != null ? ResponseEntity.ok(intro) : ResponseEntity.notFound().build();
    }

    // 소개글 삭제
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteIntro(@PathVariable String email) {
        if (profileService.deleteIntroduction(email)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

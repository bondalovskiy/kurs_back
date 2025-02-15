package com.bndlvsk.spotapp.controller;

import com.bndlvsk.spotapp.DTO.FollowersDTO;
import com.bndlvsk.spotapp.DTO.ProfileDTO;
import com.bndlvsk.spotapp.DTO.UserDTO;
import com.bndlvsk.spotapp.model.OAuth2UserWrapper;
import com.bndlvsk.spotapp.model.mapbox.Coordinates;
import com.bndlvsk.spotapp.service.SocialService;
import com.bndlvsk.spotapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.bndlvsk.spotapp.constants.SpotappConstants.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class SocialController {

    private final SocialService socialService;
    private final UserService userService;

    @GetMapping("/me/following")
    public ResponseEntity<FollowersDTO> getCurrentUserFollowing(@AuthenticationPrincipal OAuth2UserWrapper principal) {
        long userId = principal.getUserId();
        FollowersDTO followersDTO = socialService.getFollowers(userId, FOLLOWING);
        return ResponseEntity.ok(followersDTO);
    }

    @PutMapping("/follow")
    public ResponseEntity<Void> follow(@AuthenticationPrincipal OAuth2UserWrapper principal,
                                       @RequestParam("user_id") long followId) {
        long userId = principal.getUserId();
        socialService.follow(userId, followId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/unfollow")
    public ResponseEntity<Void> unfollow(@AuthenticationPrincipal OAuth2UserWrapper principal,
                                         @RequestParam("user_id") long followId) {
        long userId = principal.getUserId();
        socialService.unfollow(userId, followId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile(@RequestParam("user_id") long userId) {
        ProfileDTO userProfile = socialService.getUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }

    @GetMapping("/rank")
    public ResponseEntity<List<UserDTO>> getRank() {
        List<UserDTO> userDTOList = userService.findAllUsersOrderByPoints();
        return ResponseEntity.ok(userDTOList);
    }

    @PutMapping("/links")
    public ResponseEntity<Void> addLinks(@AuthenticationPrincipal OAuth2UserWrapper principal,
                                         @Valid @RequestBody Map<String, String> socialLinks) {
        long userId = principal.getUserId();
        socialService.updateSocialLinks(userId, socialLinks);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/location")
    public ResponseEntity<Void> saveLocation(@AuthenticationPrincipal OAuth2UserWrapper principal,
                                             @RequestBody Coordinates coordinates) {
        long userId = principal.getUserId();
        socialService.saveUserLocation(userId, coordinates.getLongitude(), coordinates.getLatitude());
        return ResponseEntity.noContent().build();
    }
}

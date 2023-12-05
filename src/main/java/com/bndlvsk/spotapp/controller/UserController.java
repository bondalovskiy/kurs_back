package com.bndlvsk.spotapp.controller;

import com.bndlvsk.spotapp.DTO.UserDTO;
import com.bndlvsk.spotapp.model.OAuth2UserWrapper;
import com.bndlvsk.spotapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal OAuth2UserWrapper principal) {
        long userId = principal.getUserId();
        UserDTO userDTO = userService.findUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/me/delete")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal OAuth2User principal) {
        userService.deleteUser((long) principal.getAttributes().get("userId"));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUser(@RequestParam String username) {
        List<UserDTO> userDTOList = username.isBlank() ? List.of() : userService.findAllMatchingUsers(username);
        return ResponseEntity.ok(userDTOList);
    }

}

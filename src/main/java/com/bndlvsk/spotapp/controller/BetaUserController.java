package com.bndlvsk.spotapp.controller;

import com.bndlvsk.spotapp.DTO.BetaUserDTO;
import com.bndlvsk.spotapp.service.BetaUserService;
import com.bndlvsk.spotapp.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beta")
@RequiredArgsConstructor
public class BetaUserController {

    private final BetaUserService betaUserService;
    private final MailService mailService;

    @GetMapping("/all")
    public ResponseEntity<List<BetaUserDTO>> getAllBetaUsers() {
        List<BetaUserDTO> dto = betaUserService.findAllBetaUsers();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/join")
    public ResponseEntity<Void> join(@RequestParam("name") String fullName, @RequestParam String email) {
        betaUserService.saveBetaUser(new BetaUserDTO(fullName, email, null, false));
        mailService.sendJoinBetaNotification();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/activate")
    public ResponseEntity<Void> activate(@RequestBody BetaUserDTO betaUserDTO) {
        betaUserService.activateUser(betaUserDTO.getEmail());
        mailService.sendAccessGrantedNotification(betaUserDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAllBetaUsers() {
        betaUserService.deleteAllBetaUsers();
        return ResponseEntity.noContent().build();
    }
}

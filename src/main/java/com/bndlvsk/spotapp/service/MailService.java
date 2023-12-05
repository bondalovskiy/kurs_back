package com.bndlvsk.spotapp.service;

import com.bndlvsk.spotapp.DTO.BetaUserDTO;

public interface MailService {
    void sendJoinBetaNotification();
    void sendAccessGrantedNotification(BetaUserDTO dto);
}

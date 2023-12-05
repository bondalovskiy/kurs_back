package com.bndlvsk.spotapp.service;

import com.bndlvsk.spotapp.DTO.BetaUserDTO;

import java.util.List;

public interface BetaUserService {

    BetaUserDTO findBetaUserByEmail(String email);

    void saveBetaUser(BetaUserDTO betaUserDTO);

    void activateUser(String email);

    List<BetaUserDTO> findAllBetaUsers();

    void deleteAllBetaUsers();

}

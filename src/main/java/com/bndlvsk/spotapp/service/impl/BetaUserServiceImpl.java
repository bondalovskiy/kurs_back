package com.bndlvsk.spotapp.service.impl;

import com.bndlvsk.spotapp.DTO.BetaUserDTO;
import com.bndlvsk.spotapp.entity.BetaUser;
import com.bndlvsk.spotapp.exception.UserNotFoundException;
import com.bndlvsk.spotapp.mapper.EntityMapper;
import com.bndlvsk.spotapp.repository.BetaUserRepository;
import com.bndlvsk.spotapp.service.BetaUserService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class BetaUserServiceImpl implements BetaUserService {

    private final BetaUserRepository betaUserRepository;

    @Override
    public BetaUserDTO findBetaUserByEmail(String email) {
        return betaUserRepository.findByEmail(email)
                .map(EntityMapper::toBetaUserDTO)
                .orElseThrow(() -> new UserNotFoundException("Beta user not found"));
    }

    @Override
    public void saveBetaUser(BetaUserDTO betaUserDTO) {
        BetaUser betaUser = new BetaUser(0, betaUserDTO.getFullName(), betaUserDTO.getEmail(), LocalDateTime.now(), betaUserDTO.isActive());
        betaUserRepository.save(betaUser);
    }

    @Override
    public void activateUser(String email) {
        betaUserRepository.findByEmail(email)
                .ifPresentOrElse(betaUser -> {
                    betaUser.setActive(true);
                    betaUserRepository.save(betaUser);
                }, () -> {
                    throw new UserNotFoundException("Beta user not found");
                });
    }

    @Override
    public List<BetaUserDTO> findAllBetaUsers() {
        return ((Collection<BetaUser>) betaUserRepository.findAll()).stream()
                .map(EntityMapper::toBetaUserDTO)
                .toList();
    }

    @Override
    public void deleteAllBetaUsers() {
        betaUserRepository.deleteAll();
    }
}

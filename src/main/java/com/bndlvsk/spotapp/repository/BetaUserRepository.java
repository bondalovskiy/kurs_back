package com.bndlvsk.spotapp.repository;

import com.bndlvsk.spotapp.entity.BetaUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BetaUserRepository extends CrudRepository<BetaUser, Long> {
    boolean existsByEmail(String email);
    Optional<BetaUser> findByEmail(String email);
}

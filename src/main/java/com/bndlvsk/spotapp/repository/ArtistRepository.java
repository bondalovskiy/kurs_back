package com.bndlvsk.spotapp.repository;

import com.bndlvsk.spotapp.entity.UserArtist;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface ArtistRepository extends CrudRepository<UserArtist, Long> {
    Optional<UserArtist> findFirstByUserIdAndRangeOrderByDateDesc(long userId, String range);
    Collection<UserArtist> findAllByUserId(long userId);
}

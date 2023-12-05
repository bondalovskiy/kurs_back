package com.bndlvsk.spotapp.repository;

import com.bndlvsk.spotapp.entity.UserTrack;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface TrackRepository extends CrudRepository<UserTrack, Long> {
    Optional<UserTrack> findFirstByUserIdAndRangeOrderByDateDesc(long userId, String range);
    Collection<UserTrack> findAllByUserId(long userId);

}

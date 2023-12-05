package com.bndlvsk.spotapp.service;

import com.bndlvsk.spotapp.DTO.UserDTO;

import java.util.Collection;

public interface LocationService {
    Collection<UserDTO> findBestMatchingUsers(long userId);
    Collection<UserDTO> findUsersNearby(long userId);
    UserDTO calculateDistanceHaversine(UserDTO user1, UserDTO user2);
    String getMapAccessToken();
}

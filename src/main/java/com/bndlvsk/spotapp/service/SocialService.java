package com.bndlvsk.spotapp.service;

import com.bndlvsk.spotapp.DTO.FollowersDTO;
import com.bndlvsk.spotapp.DTO.ProfileDTO;
import com.bndlvsk.spotapp.constants.SpotappConstants;
import com.bndlvsk.spotapp.entity.User;

import java.util.Map;

public interface SocialService {

    ProfileDTO getUserProfile(long userId);

    FollowersDTO getFollowers(long userId, SpotappConstants type);

    User follow(long userId, long followId);

    User unfollow(long userId, long unfollowId);

    User updatePoints(long userId, int points);

    User updateSocialLinks(long userId, Map<String, String> socialLinks);

    void saveUserLocation(long userId, Double longitude, Double latitude);

}

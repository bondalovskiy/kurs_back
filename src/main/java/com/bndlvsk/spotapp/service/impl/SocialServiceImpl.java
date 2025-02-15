package com.bndlvsk.spotapp.service.impl;

import com.bndlvsk.spotapp.DTO.FollowersDTO;
import com.bndlvsk.spotapp.DTO.ProfileDTO;
import com.bndlvsk.spotapp.DTO.UserDTO;
import com.bndlvsk.spotapp.constants.SpotappConstants;
import com.bndlvsk.spotapp.entity.User;
import com.bndlvsk.spotapp.exception.UserNotFoundException;
import com.bndlvsk.spotapp.mapper.EntityMapper;
import com.bndlvsk.spotapp.repository.UserRepository;
import com.bndlvsk.spotapp.service.SocialService;
import com.bndlvsk.spotapp.service.StatsService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@AllArgsConstructor
@Transactional
public class SocialServiceImpl implements SocialService {

    private final static Supplier<UserNotFoundException> USER_NOT_FOUND_EXCEPTION_SUPPLIER = () -> new UserNotFoundException("User not found");

    private final UserRepository userRepository;
    private final StatsService statsService;

    @Override
    public ProfileDTO getUserProfile(long userId) {
        return userRepository.findById(userId)
                .map(foundUser -> createProfileDTO(foundUser, userId))
                .orElseThrow(USER_NOT_FOUND_EXCEPTION_SUPPLIER);
    }

    @Override
    public FollowersDTO getFollowers(long userId, SpotappConstants type) {
        return userRepository.findById(userId)
                .map(foundUser -> {
                    List<User> followers = type == SpotappConstants.FOLLOWING ? foundUser.getFollowing() : foundUser.getFollowers();
                    List<UserDTO> list = followers.stream()
                            .map(EntityMapper::toUserDTO)
                            .toList();
                    return new FollowersDTO(list.size(), list);
                }).orElseThrow(USER_NOT_FOUND_EXCEPTION_SUPPLIER);
    }

    @Override
    public User follow(long userId, long followId) {
        User user = userRepository.findById(userId)
                .map(foundUser -> {
                    foundUser.addFollower(
                            userRepository.findById(followId)
                                    .orElseThrow(USER_NOT_FOUND_EXCEPTION_SUPPLIER)
                    );
                    return foundUser;
                }).orElseThrow(USER_NOT_FOUND_EXCEPTION_SUPPLIER);
        return userRepository.save(user);
    }

    @Override
    public User unfollow(long userId, long unfollowId) {
        User user = userRepository.findById(userId)
                .map(foundUser -> {
                    foundUser.removeFollower(
                            userRepository.findById(unfollowId)
                                    .orElseThrow(USER_NOT_FOUND_EXCEPTION_SUPPLIER)
                    );
                    return foundUser;
                }).orElseThrow(USER_NOT_FOUND_EXCEPTION_SUPPLIER);
        return userRepository.save(user);
    }

    @Override
    public User updatePoints(long userId, int points) {
        return userRepository.save(userRepository.findById(userId)
                .map(user -> {
                    long score = user.getUserStats().getPoints() + points < 0 ? 0 : user.getUserStats().getPoints() + points;
                    int battleCount = points <= 0 ? user.getUserStats().getBattleCount() : user.getUserStats().getBattleCount() + 1;
                    user.getUserStats().setPoints(score);
                    user.getUserStats().setBattleCount(battleCount);
                    return user;
                }).orElseThrow(USER_NOT_FOUND_EXCEPTION_SUPPLIER));
    }

    @Override
    public User updateSocialLinks(long userId, Map<String, String> socialLinks) {
        return userRepository.save(userRepository.findById(userId)
                .map(user -> {
                    user.getUserInfo()
                            .setFb(socialLinks.getOrDefault("fb", null))
                            .setIg(socialLinks.getOrDefault("ig", null))
                            .setTwitter(socialLinks.getOrDefault("twitter", null));
                    return user;
                }).orElseThrow(USER_NOT_FOUND_EXCEPTION_SUPPLIER));
    }

    @Override
    public void saveUserLocation(long userId, Double longitude, Double latitude) {
        userRepository.save(userRepository.findById(userId)
                .map(user -> {
                    user.getUserInfo()
                            .setLongitude(longitude)
                            .setLatitude(latitude);
                    return user;
                }).orElseThrow(USER_NOT_FOUND_EXCEPTION_SUPPLIER));
    }

    //helpers
    private ProfileDTO createProfileDTO(User user, long userId) {
        return new ProfileDTO(
                user.getId(),
                user.getExternalId(),
                user.getUsername(),
                user.getImage(),
                user.getJoinDate(),
                user.getFollowing().stream()
                        .map(EntityMapper::toUserDTO)
                        .toList(),
                user.getFollowers().stream()
                        .map(EntityMapper::toUserDTO)
                        .toList(),
                statsService.getUserTracks(userId, "long").getTracks(),
                statsService.getUserArtists(userId, "long").getArtists(),
                Map.of(
                        "energy", user.getUserStats().getEnergy(),
                        "tempo", user.getUserStats().getTempo(),
                        "boringness", user.getUserStats().getBoringness(),
                        "mainstream", user.getUserStats().getMainstream()
                ),
                user.getUserInfo().getIg(),
                user.getUserInfo().getFb(),
                user.getUserInfo().getTwitter(),
                user.getUserStats().getPoints()
        );
    }
}

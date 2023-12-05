package com.bndlvsk.spotapp.mapper;

import com.bndlvsk.spotapp.DTO.BetaUserDTO;
import com.bndlvsk.spotapp.DTO.UserDTO;
import com.bndlvsk.spotapp.entity.BetaUser;
import com.bndlvsk.spotapp.entity.User;
import com.bndlvsk.spotapp.model.mapbox.Coordinates;
import com.bndlvsk.spotapp.model.spotify.Image;

import java.util.List;

public class EntityMapper {
    public static UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(Long.toString(user.getId()))
                .email(user.getEmail())
                .name(user.getUsername())
                .images(List.of(new Image(user.getImage(), null, null)))
                .points(user.getUserStats().getPoints())
                .coordinates(new Coordinates(user.getUserInfo().getLongitude(), user.getUserInfo().getLatitude()))
                .build();
    }

    public static BetaUserDTO toBetaUserDTO(BetaUser betaUser) {
        return new BetaUserDTO(
                betaUser.getFullName(),
                betaUser.getEmail(),
                betaUser.getDate().toString(),
                betaUser.isActive()
        );
    }
}

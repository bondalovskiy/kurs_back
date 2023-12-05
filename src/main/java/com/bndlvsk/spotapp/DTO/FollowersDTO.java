package com.bndlvsk.spotapp.DTO;

import lombok.Value;

import java.util.List;

@Value
public class FollowersDTO {
    long size;
    List<UserDTO> users;
}

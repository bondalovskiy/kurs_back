package com.bndlvsk.spotapp.service;

import com.bndlvsk.spotapp.DTO.UserDTO;

import java.util.Collection;
import java.util.List;

public interface MapAPIService {
    Collection<UserDTO> getDistanceMatrix(List<UserDTO> users);
    UserDTO getReverseGeocoding(UserDTO user);
}

package com.bndlvsk.spotapp.service;

import com.bndlvsk.spotapp.DTO.*;
import com.bndlvsk.spotapp.entity.User;


import java.util.Collection;
import java.util.List;

public interface UserService {

    Collection<UserDTO> findAllUsers();

    UserDTO findUserByEmail(String email);

    UserDTO findUserById(long userId);

    List<UserDTO> findAllMatchingUsers(String username);

    List<UserDTO> findAllUsersOrderByPoints();

    UserDTO saveUser(User user);

    void deleteUser(long id);

}

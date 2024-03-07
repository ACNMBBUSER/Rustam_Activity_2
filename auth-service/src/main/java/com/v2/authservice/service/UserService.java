package com.v2.authservice.service;

import com.v2.authservice.entity.Role;
import com.v2.authservice.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername (String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String roleName);
    User removeRoleToUser(String username, String roleName);
    void deleteUserById(Long id);
    List<User> getAllUsers();
}

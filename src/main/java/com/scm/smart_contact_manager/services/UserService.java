package com.scm.smart_contact_manager.services;

import com.scm.smart_contact_manager.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

      User saveUser(User user);

      Optional<User> getUserById(String id);

      Optional<User> updateUser(User user);

      void deleteUser(String id);

      boolean isUserExist(String userId);

      boolean isUserExistByEmail(String email);

      List<User> getAllUsers();

      User getUserByEmail(String email);

}

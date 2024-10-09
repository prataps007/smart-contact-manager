package com.scm.smart_contact_manager.services.Impl;

import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.helper.ResourceNotFoundException;
import com.scm.smart_contact_manager.repositories.UserRepo;
import com.scm.smart_contact_manager.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        // generate user id
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        // encode password

        User saved = userRepo.save(user);

        return saved;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user2.setName(user.getName());
        user2.setAbout(user.getAbout());
        user2.setPassword(user.getPassword());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProviders(user.getProviders());
        user2.setProviderUserId(user.getProviderUserId());

        User updated = userRepo.save(user2);

        return Optional.ofNullable(updated);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user2 != null;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user2 = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user2 != null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
}

package com.indian.indian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.indian.indian.Utils.PasswordUtils;
import com.indian.indian.entity.User;
import com.indian.indian.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser(User user) {
        // check if user exist by email address
        if (getUserByEmail(user.getEmail()).isPresent()) {
            return "emailExist";
        } else if (getUserByMobile(user.getMobile()).isPresent()) {
            return "mobileExist";
        } else {
            String plainPass = user.getPassword();
            String hashedpw = PasswordUtils.generatePassword(plainPass);
            user.setPassword(hashedpw);
            try {
                userRepository.save(user);
                return "created";
            } catch (Exception e) {
                return "error";
            }
        }
    }

    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setMobile(userDetails.getMobile());
        user.setPassword(userDetails.getPassword()); // Consider hashing the password
        user.setDateOfBirth(userDetails.getDateOfBirth());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

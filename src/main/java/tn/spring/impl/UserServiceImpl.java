package tn.spring.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tn.spring.entity.Role;
import tn.spring.entity.User;
import tn.spring.interfac.UserInterface;
import tn.spring.repo.UserRepository;

@Service
public class UserServiceImpl implements UserInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    @Override
    public void addUser(User user) {
        // Check if a user with the same email already exists
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User with this email already exists");
        }

        // Set the role based on the user's registration
        user.setRole(Role.USER);

        // Encrypt the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Save the user
        userRepository.save(user);
    }

    @Override
    public User retrieveUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User retrieveUserById(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }
    
    @Override
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
    
    @Override
    public boolean changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email);
        
        if (user != null && passwordEncoder.matches(currentPassword, user.getPassword())) {
            // Encrypt the new password
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            // Update the user's password
            user.setPassword(encodedNewPassword);
            // Save the updated user
            userRepository.save(user);
            return true; // Password changed successfully
        } else {
            return false; // Current password doesn't match
        }
    }
    


}

    

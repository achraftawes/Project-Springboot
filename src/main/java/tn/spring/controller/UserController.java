package tn.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.spring.entity.Role;
import tn.spring.entity.User;
import tn.spring.impl.UserServiceImpl;
import tn.spring.security.JwtTokenUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return ResponseEntity.ok("User registration successful!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        User authenticatedUser = userService.authenticateUser(email, password);

        if (authenticatedUser != null) {
            // Generate JWT token upon successful login
            String token = jwtTokenUtil.generateToken(email);
            
            // Create a JSON object to wrap the token
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Invalid email or password"));
        }
    }
    

 // Profile endpoint
    @GetMapping("/profile")
    public User viewProfile(@RequestHeader("Authorization") String token) {
        String email = jwtTokenUtil.extractEmail(token.substring(7));
        return userService.retrieveUserByEmail(email);
    }

    @GetMapping("/list-users")
    public ResponseEntity<?> listAllUsers(@RequestHeader("Authorization") String token) {
        String email = jwtTokenUtil.extractEmail(token.substring(7));
        User user = userService.retrieveUserByEmail(email);

        if (user.getRole() == Role.ADMIN) {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "You are not authorized to view all users"));
        }
    }

    @GetMapping("/show-user-profile/{user_id}")
    public ResponseEntity<?> showUserProfile(@PathVariable Long user_id) {
        User userProfile = userService.retrieveUserById(user_id);
        if (userProfile != null) {
            return ResponseEntity.ok(userProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String token,
                                                 @RequestBody Map<String, String> passwordDetails) {
        String email = jwtTokenUtil.extractEmail(token.substring(7));
        String currentPassword = passwordDetails.get("currentPassword");
        String newPassword = passwordDetails.get("newPassword");
        
        boolean passwordChanged = userService.changePassword(email, currentPassword, newPassword);
        if (passwordChanged) {
            return ResponseEntity.ok("Password changed successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to change password. Please check your current password.");
        }
    }
    
    
    
    
}





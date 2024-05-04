package tn.spring.interfac;

import java.util.List;
import org.springframework.stereotype.Repository;
import tn.spring.entity.User;

@Repository
public interface UserInterface {
    
	 void addUser(User user);

    User retrieveUserByEmail(String email);

    List<User> getAllUsers();

    User retrieveUserById(Long user_id);

    User authenticateUser(String email, String password);
    
    boolean changePassword(String email, String currentPassword, String newPassword);
    
    
}
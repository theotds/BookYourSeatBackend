package com.reservation.Service;

import com.reservation.Entity.User;
import com.reservation.Repository.UserRepository;
import com.reservation.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
@CrossOrigin("*")

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByusername(String email) {
        return userRepository.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @CrossOrigin("*")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public boolean changeUserRole(Long userId, Role newRole) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.setRol(newRole);
            userRepository.save(user);
            return true;
        } else {
            System.out.println("User is null");
            return false;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}

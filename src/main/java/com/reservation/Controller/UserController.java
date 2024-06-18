package com.reservation.Controller;

import com.reservation.Entity.Restaurant;
import com.reservation.Entity.User;
import com.reservation.Service.UserService;
import com.reservation.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/id")
    public ResponseEntity<Long> getUserId(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.findByusername(userDetails.getUsername());
        return ResponseEntity.ok(Long.valueOf(user.getId()));
    }

    @PostMapping("/users/{userId}/changeRole/{role}")
    public ResponseEntity<String> changeUserRole(@PathVariable Long userId, @PathVariable("role") String roleStr) {
        Role newRole;
        try {
            newRole = Role.valueOf(roleStr.toUpperCase());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role specified.");
        }

        boolean updateResult = userService.changeUserRole(userId, newRole);
        if (updateResult) {
            return ResponseEntity.ok("User role updated to " + newRole + " successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update user role.");
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
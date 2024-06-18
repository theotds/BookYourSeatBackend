package com.reservation.Controller;

import com.reservation.DTO.AuthResponseDTO;
import com.reservation.DTO.LoginDTO;
import com.reservation.Entity.User;
import com.reservation.Repository.UserRepository;
import com.reservation.Service.JwtService;
import com.reservation.utils.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @CrossOrigin("*")

    public ResponseEntity<?> signIn(@RequestBody LoginDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = jwtService.generateToken(authentication);
            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("USER");

            Long userId = ((User) authentication.getPrincipal()).getId(); // Pobierz userId z zalogowanego użytkownika

            AuthResponseDTO response = new AuthResponseDTO(jwtToken, role, userId); // Dodaj userId do konstruktora
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            logger.error("Incorrect email or password: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        } catch (Exception e) {
            logger.error("Authentication failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed: " + e.getMessage());
        }
    }


    @PostMapping("/register")
    @CrossOrigin("*")

    public ResponseEntity<String> signUp(@RequestBody User user) {
        if (userExists(user.getUsername()) || isInvalidUser(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
        }
        try {
            registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            logger.error("An error occurred during registration: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration. Please try again.");
        }
    }

    private Authentication authenticateUser(String email, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    private boolean isInvalidUser(User user) {
        return user == null || user.getUsername() == null || user.getPassword() == null;
    }

    private boolean userExists(String email) {
        return userRepository.findByUsername(email).isPresent();
    }

    private void registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword().trim());
        user.setPassword(encodedPassword);
        user.setRol(Role.USER);
        userRepository.save(user);
    }
}
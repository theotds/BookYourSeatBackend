package com.reservation.config;

import com.reservation.Entity.User;
import com.reservation.Repository.UserRepository;
import com.reservation.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractusername(jwt);
        if (userEmail == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<User> userOptional = userRepository.findByUsername(userEmail);
        if (userOptional.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails user = userOptional.get();
        UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );

        authUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authUser);
        filterChain.doFilter(request, response);
    }
}
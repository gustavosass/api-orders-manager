package com.gustavosass.orders.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavosass.orders.mapper.UserMapper;
import com.gustavosass.orders.model.user.User;
import com.gustavosass.orders.model.user.dto.AuthenticationRequestDTO;
import com.gustavosass.orders.model.user.dto.AuthenticationResponseDTO;
import com.gustavosass.orders.model.user.dto.RegisterDTO;
import com.gustavosass.orders.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserMapper userMapper;
    
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    public User register(RegisterDTO registerDTO) {

        if (userService.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("Email already exist");
        }

        User user = userMapper.toEntity(registerDTO);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        
        return userService.create(user);
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        var user = userService.findByEmail(request.getEmail());
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
                
        return builderAuthenticationResponseDTO(accessToken, refreshToken);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        
        if (userEmail != null) {
            var user = userService.findByEmail(userEmail);
            
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);    
                var authResponse = builderAuthenticationResponseDTO(accessToken, refreshToken);
                
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private AuthenticationResponseDTO builderAuthenticationResponseDTO(String jwtToken, String refreshToken) {
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .expiresIn(jwtExpiration)
                .build();
    }
}
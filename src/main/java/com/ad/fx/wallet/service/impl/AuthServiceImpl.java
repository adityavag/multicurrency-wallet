package com.ad.fx.wallet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ad.fx.wallet.dto.AuthResponse;
import com.ad.fx.wallet.dto.LoginRequest;
import com.ad.fx.wallet.dto.RegisterRequest;
import com.ad.fx.wallet.exception.EmailAlreadyExistsException;
import com.ad.fx.wallet.exception.InvalidCredentialsException;
import com.ad.fx.wallet.model.User;
import com.ad.fx.wallet.repository.UserRepository;
import com.ad.fx.wallet.service.AuthService;
import com.ad.fx.wallet.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setName(request.name());
        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getEmail());
        return new AuthResponse(
                token,
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getId());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid Credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(
                token,
                user.getName(),
                user.getEmail(),
                user.getId());
    }

}

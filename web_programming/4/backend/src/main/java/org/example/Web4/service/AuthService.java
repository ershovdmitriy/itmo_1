package org.example.Web4.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.Web4.dto.AuthRequest;
import org.example.Web4.dto.AuthResponse;
import org.example.Web4.model.User;
import org.example.Web4.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request, HttpServletRequest httpRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            new HttpSessionSecurityContextRepository().saveContext(
                    SecurityContextHolder.getContext(),
                    httpRequest,
                    null
            );

            return new AuthResponse("Вход выполнен", request.getUsername());
        } catch (Exception e) {
            throw new RuntimeException("Неверное имя пользователя или пароль");
        }
    }
}
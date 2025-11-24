package com.library.management.service;

import com.library.management.dto.AuthRequest;
import com.library.management.dto.AuthResponse;
import com.library.management.dto.RegisterRequest;
import com.library.management.model.Librarian;
import com.library.management.model.Student;
import com.library.management.model.User;
import com.library.management.repository.UserRepository;
import com.library.management.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public AuthResponse register(RegisterRequest request) {
        if (!request.getEmail().endsWith("@usiu.ac.ke")) {
            throw new RuntimeException("Email must be from @usiu.ac.ke domain");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        
        User user;
        if ("STUDENT".equalsIgnoreCase(request.getUserType())) {
            user = new Student(request.getName(), request.getEmail(), 
                              passwordEncoder.encode(request.getPassword()));
        } else if ("LIBRARIAN".equalsIgnoreCase(request.getUserType())) {
            user = new Librarian(request.getName(), request.getEmail(), 
                                passwordEncoder.encode(request.getPassword()));
        } else {
            throw new RuntimeException("Invalid user type");
        }
        
        user.register();
        user = userRepository.save(user);
        
        String token = jwtUtil.generateToken(user.getEmail(), user.getUserType());
        return new AuthResponse(token, user.getUserType(), user.getId(), user.getName(), user.getEmail());
    }
    
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        String token = jwtUtil.generateToken(user.getEmail(), user.getUserType());
        return new AuthResponse(token, user.getUserType(), user.getId(), user.getName(), user.getEmail());
    }
}

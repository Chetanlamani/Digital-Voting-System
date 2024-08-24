package com.example.election.service;

import com.example.election.entity.UserEntity;
import com.example.election.repository.UserRepository;
import com.example.election.response.AuthenticationResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserEntity request){
        UserEntity user=new UserEntity();
        BeanUtils.copyProperties(request,user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user=userRepository.save(user);

        String token=jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse login(UserDetails request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserEntity user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.generateToken(user);

        return new AuthenticationResponse(token);

    }
}

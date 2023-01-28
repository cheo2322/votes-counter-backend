package com.elections.counter.service.impl;

import com.elections.counter.document.Role;
import com.elections.counter.document.User;
import com.elections.counter.dto.request.LoginDto;
import com.elections.counter.dto.request.SignUpDto;
import com.elections.counter.repository.RoleRepository;
import com.elections.counter.repository.UserRepository;
import com.elections.counter.security.JwtTokenProvider;
import com.elections.counter.service.AuthService;
import java.util.Collections;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  public AuthServiceImpl(AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder,
      UserRepository userRepository, RoleRepository roleRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public String login(LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return jwtTokenProvider.generateToken(authentication);
  }

  @Override
  public String register(SignUpDto signUpDto) {
    if(userRepository.existsByUsername(signUpDto.getUsername())){
      throw new RuntimeException("");
    }

    if(userRepository.existsByEmail(signUpDto.getEmail())){
      throw new RuntimeException("");
    }

    User user = User.builder()
        .username(signUpDto.getUsername())
        .email(signUpDto.getEmail())
        .password(passwordEncoder.encode(signUpDto.getPassword()))
        .build();

    Role role = roleRepository.findByName("ROLE_ADMIN").get();
    user.setRoles(Collections.singleton(role));

    userRepository.save(user);

    return "User registered";
  }
}

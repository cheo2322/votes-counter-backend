package com.elections.counter.controller;

import com.elections.counter.dto.request.LoginDto;
import com.elections.counter.dto.request.SignUpDto;
import com.elections.counter.dto.response.JWTAuthResponse;
import com.elections.counter.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counter_api/v1/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/signIn")
  public ResponseEntity<JWTAuthResponse> signIn(@RequestBody LoginDto loginDto) {
    String token = authService.login(loginDto);

    JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
    jwtAuthResponse.setAccessToken(token);

    return ResponseEntity.ok(jwtAuthResponse);
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody SignUpDto signUpDto) {
    String response = authService.register(signUpDto);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}

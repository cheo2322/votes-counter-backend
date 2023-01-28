package com.elections.counter.service;

import com.elections.counter.dto.request.LoginDto;
import com.elections.counter.dto.request.SignUpDto;

public interface AuthService {

  String login(LoginDto loginDto);

  String register(SignUpDto signUpDto);
}

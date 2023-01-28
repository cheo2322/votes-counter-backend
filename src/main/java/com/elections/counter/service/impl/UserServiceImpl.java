package com.elections.counter.service.impl;

import com.elections.counter.dto.response.UserDto;
import com.elections.counter.repository.UserRepository;
import com.elections.counter.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDto getUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .map(user -> UserDto.builder().username(user.getUsername()).build())
        .orElseThrow(() -> new RuntimeException("User not found!"));
  }
}

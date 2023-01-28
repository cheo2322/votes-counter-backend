package com.elections.counter.service;

import com.elections.counter.dto.response.UserDto;

public interface IUserService {

  UserDto getUserByUsername(String username);
}

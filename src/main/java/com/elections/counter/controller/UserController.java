package com.elections.counter.controller;

import com.elections.counter.dto.response.UserDto;
import com.elections.counter.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counter_api/v1/user")
@Slf4j
public class UserController {

  @Autowired
  IUserService userService;

  @GetMapping("/{username}")
  ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
    log.info("GET getUserByUsername [username={}]", username);

    return ResponseEntity.ok(userService.getUserByUsername(username));
  }
}

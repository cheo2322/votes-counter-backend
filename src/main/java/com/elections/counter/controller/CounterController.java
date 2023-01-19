package com.elections.counter.controller;

import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.service.CounterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counter_api/v1")
public class CounterController {

  @Autowired
  CounterService counterService;

  @PostMapping(path = "/candidate")
  @ResponseStatus(value = HttpStatus.CREATED)
  @ResponseBody
  public CandidateResponse createCandidate(@RequestBody CandidateRequest candidateRequest) {
    return counterService.createCandidate(candidateRequest);
  }

  @PutMapping(path = "/candidate/{id}/votes/add")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public long addVotes(@PathVariable String id, @RequestParam long votes) {
    return counterService.addVotes(id, votes);
  }

  @GetMapping("/candidate")
  public List<CandidateDto> getCandidates() {
    return counterService.getAllCandidates();
  }

  @GetMapping("/candidate/:id")
  public void getCandidate(String id) {

  }
}

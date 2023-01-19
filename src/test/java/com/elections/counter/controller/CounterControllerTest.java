package com.elections.counter.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.service.CounterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CounterControllerTest {

  JacksonTester<CandidateRequest> candidateRequest;
  JacksonTester<CandidateResponse> candidateResponse;
  private MockMvc mvc;
  @Mock
  private CounterService counterService;
  @InjectMocks
  private CounterController counterController;

  @BeforeEach
  public void setup() {
    JacksonTester.initFields(this, new ObjectMapper());

    mvc = MockMvcBuilders.standaloneSetup(counterController)
        .build();
  }
  @SneakyThrows
  @Test
  void createCandidate() {
    given(counterService.createCandidate(any(CandidateRequest.class)))
        .willReturn(CandidateResponse.builder().name("Bob").build());

    MockHttpServletResponse response = mvc.perform(
        post("/counter_api/v1/candidate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(candidateRequest.write(CandidateRequest.builder()
                .name("Bob")
                .build()
                ).getJson())
            ).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.getContentAsString()).isEqualTo(
        candidateResponse.write(CandidateResponse.builder().name("Bob").build()).getJson());
  }
}
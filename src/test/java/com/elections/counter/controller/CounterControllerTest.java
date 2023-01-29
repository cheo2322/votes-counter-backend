package com.elections.counter.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.elections.counter.document.enums.DeskType;
import com.elections.counter.document.enums.Position;
import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.dto.response.VoteDto;
import com.elections.counter.dto.response.VotesAddedResponse;
import com.elections.counter.service.CandidateService;
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
  JacksonTester<VoteDto> voteDto;
  JacksonTester<VotesAddedResponse> votesAddedResponse;
  private MockMvc mvc;
  @Mock
  private CandidateService candidateService;
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
  void shouldCreateAndPostANewCandidate() {
    CandidateResponse response = CandidateResponse.builder().name("Bob").build();

    given(candidateService.createCandidate(any(CandidateRequest.class)))
        .willReturn(response);

    MockHttpServletResponse httpResponse = mvc.perform(
        post("/counter_api/v1/candidate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(candidateRequest.write(
                CandidateRequest.builder()
                    .name("Bob")
                    .lastName("Singer")
                    .position(Position.ALCALDE)
                    .build()
                ).getJson())
            ).andReturn().getResponse();

    assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(httpResponse.getContentAsString())
        .isEqualTo(candidateResponse.write(response).getJson());
  }

  @SneakyThrows
  @Test
  void shouldAddVotesToACandidate() {
    VotesAddedResponse response1 = VotesAddedResponse.builder()
        .votesAdded(10L)
        .totalVotesOnDesk(10L)
        .parish("URCUQUI")
        .precinct("UNIDAD_EDUCATIVA_YACHAY")
        .desk(1)
        .deskType(DeskType.FEMENINO)
        .build();

    given(candidateService.addVotesToCandidate(anyString(), any(VoteDto.class)))
        .willReturn(response1);

    MockHttpServletResponse httpResponse = mvc.perform(
        patch("/counter_api/v1/candidate/0/votes/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(voteDto.write(
                VoteDto.builder()
                    .votesAmount(10L)
                    .parish("URCUQUI")
                    .precinct("UNIDAD_EDUCATIVA_YACHAY")
                    .desk(1)
                    .deskType(DeskType.FEMENINO)
                    .build()
                ).getJson())
            ).andReturn()
        .getResponse();

    assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(httpResponse.getContentAsString())
        .isEqualTo(votesAddedResponse.write(response1).getJson());
  }
}
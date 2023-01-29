package com.elections.counter.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.elections.counter.document.enums.DeskType;
import com.elections.counter.document.enums.Position;
import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.dto.response.VoteDto;
import com.elections.counter.dto.response.VotesAddedResponse;
import com.elections.counter.dto.response.vote.DeskVoteDto;
import com.elections.counter.dto.response.vote.ParishVoteDto;
import com.elections.counter.dto.response.vote.PrecinctVoteDto;
import com.elections.counter.service.CandidateService;
import com.elections.counter.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
  JacksonTester<List<CandidateDto>> candidateDtos;
  JacksonTester<Set<ParishVoteDto>> parishVoteDtosResponse;
  private MockMvc mvc;

  @Mock
  private CandidateService candidateService;

  @Mock
  private VoteService voteService;

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
    VotesAddedResponse response = VotesAddedResponse.builder()
        .votesAdded(10L)
        .totalVotesOnDesk(10L)
        .parish("URCUQUI")
        .precinct("UNIDAD_EDUCATIVA_YACHAY")
        .desk(1)
        .deskType(DeskType.FEMENINO)
        .build();

    given(candidateService.addVotesToCandidate(anyString(), any(VoteDto.class)))
        .willReturn(response);

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
        .isEqualTo(votesAddedResponse.write(response).getJson());
  }

  @SneakyThrows
  @Test
  void shouldGetAllCandidates() {
    CandidateDto candidateDto = CandidateDto.builder()
        .id("id")
        .name("Name")
        .lastName("LastName")
        .totalVotes(10L)
        .position("PREFECTO")
        .build();

    List<CandidateDto> response = Collections.singletonList(candidateDto);

    given(candidateService.getAllCandidates())
        .willReturn(response);

    MockHttpServletResponse httpResponse = mvc.perform(
        get("/counter_api/v1/candidate"))
        .andReturn()
        .getResponse();

    assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(httpResponse.getContentAsString())
        .isEqualTo(candidateDtos.write(response).getJson());
  }

  @SneakyThrows
  @Test
  void shouldGetVotesByDesk() {
    ParishVoteDto parishVoteDto = ParishVoteDto.builder()
        .parish("PABLO_ARENAS")
        .votesByPrecinct(Collections.singleton(
            PrecinctVoteDto.builder()
                .precinct("UNIDAD_EDUCATIVA_PABLO_ARENAS")
                .votesByDesk(Collections.singleton(
                    DeskVoteDto.builder()
                        .desk(1)
                        .deskType(DeskType.MASCULINO)
                        .amount(10L)
                        .build()
                ))
                .build())
        ).build();

    Set<ParishVoteDto> response = Collections.singleton(parishVoteDto);

    given(voteService.getVotesByDesk(anyString()))
        .willReturn(response);

    MockHttpServletResponse httpResponse = mvc.perform(
        get("/counter_api/v1/candidate/0/votes/desk"))
        .andReturn()
        .getResponse();

    assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(httpResponse.getContentAsString())
        .isEqualTo(parishVoteDtosResponse.write(response).getJson());
  }
}
package org.webppo.clubcommunity_backend.controller.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.webppo.clubcommunity_backend.builder.TestBuilder;
import org.webppo.clubcommunity_backend.controller.board.recruitment.RecruitmentBoardController;
import org.webppo.clubcommunity_backend.dto.board.recruitment.RecruitmentBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.recruitment.RecruitmentBoardDto;
import org.webppo.clubcommunity_backend.dto.board.recruitment.RecruitmentBoardUpdateRequest;
import org.webppo.clubcommunity_backend.restdocs.AbstractRestDocsTests;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.board.recruitment.RecruitmentBoardService;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecruitmentBoardController.class)
public class RecruitmentBoardControllerTest extends AbstractRestDocsTests {

    @MockBean
    private RecruitmentBoardService recruitmentBoardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_whenValidRequest_thenReturnRecruitmentBoardDto() throws Exception {
        // Given
        Long memberId = 1L;
        RecruitmentBoardCreateRequest request = TestBuilder.createRecruitmentBoardCreateRequest();
        RecruitmentBoardDto recruitmentBoardDto = TestBuilder.createRecruitmentBoardDto();
        given(recruitmentBoardService.create(Mockito.anyLong(), Mockito.any(RecruitmentBoardCreateRequest.class))).willReturn(recruitmentBoardDto);

        // When, Then
        try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
            mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

            mockMvc.perform(post("/api/boards/recruitment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.title").value("Test Recruitment Title"))
                    .andDo(document("recruitment-board-controller/create",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                    fieldWithPath("title").description("게시물 제목"),
                                    fieldWithPath("content").description("게시물 내용"),
                                    fieldWithPath("clubId").description("클럽 ID")
                            ),
                            responseFields(
                                    fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시물 내용")
                            )
                    ));
        }
    }

    @Test
    void update_whenValidRequest_thenReturnUpdatedRecruitmentBoardDto() throws Exception {
        // Given
        Long memberId = 1L;
        Long boardId = 1L;
        RecruitmentBoardUpdateRequest updateRequest = TestBuilder.createRecruitmentBoardUpdateRequest();
        RecruitmentBoardDto recruitmentBoardDto = TestBuilder.createUpdatedRecruitmentBoardDto();
        given(recruitmentBoardService.update(Mockito.anyLong(), Mockito.any(RecruitmentBoardUpdateRequest.class))).willReturn(recruitmentBoardDto);

        // When, Then
        try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
            mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

            mockMvc.perform(patch("/api/boards/recruitment/{id}", boardId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.title").value("Updated Recruitment Title"))
                    .andDo(document("recruitment-board-controller/update",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            pathParameters(
                                    parameterWithName("id").description("게시물 ID")
                            ),
                            requestFields(
                                    fieldWithPath("title").description("게시물 제목"),
                                    fieldWithPath("content").description("게시물 내용")
                            ),
                            responseFields(
                                    fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시물 내용")
                            )
                    ));
        }
    }

    @Test
    void read_whenValidRequest_thenReturnRecruitmentBoardDto() throws Exception {
        // Given
        Long boardId = 1L;
        RecruitmentBoardDto recruitmentBoardDto = TestBuilder.createRecruitmentBoardDto();
        given(recruitmentBoardService.read(boardId)).willReturn(recruitmentBoardDto);

        // When, Then
        mockMvc.perform(get("/api/boards/recruitment/{id}", boardId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Recruitment Title"))
                .andDo(document("recruitment-board-controller/read",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시물 내용")
                        )
                ));
    }

    @Test
    void readAll_whenValidRequest_thenReturnRecruitmentBoardDtoList() throws Exception {
        // Given
        Long clubId = 1L;
        List<RecruitmentBoardDto> recruitmentBoardDtos = List.of(TestBuilder.createRecruitmentBoardDto());
        given(recruitmentBoardService.readAll(clubId)).willReturn(recruitmentBoardDtos);

        // When, Then
        mockMvc.perform(get("/api/boards/recruitment/club/{clubId}", clubId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Test Recruitment Title"))
                .andDo(document("recruitment-board-controller/readAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("clubId").description("클럽 ID")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("게시물 내용")
                        )
                ));
    }

    @Test
    void delete_whenValidRequest_thenReturnVoid() throws Exception {
        // Given
        Long boardId = 1L;
        willDoNothing().given(recruitmentBoardService).delete(boardId);

        // When, Then
        mockMvc.perform(delete("/api/boards/recruitment/{id}", boardId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("recruitment-board-controller/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시물 ID")
                        )
                ));
    }
}

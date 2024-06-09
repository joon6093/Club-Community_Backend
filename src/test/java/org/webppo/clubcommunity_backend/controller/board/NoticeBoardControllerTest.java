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
import org.webppo.clubcommunity_backend.controller.board.notice.NoticeBoardController;
import org.webppo.clubcommunity_backend.dto.board.notice.NoticeBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.notice.NoticeBoardDto;
import org.webppo.clubcommunity_backend.dto.board.notice.NoticeBoardUpdateRequest;
import org.webppo.clubcommunity_backend.restdocs.AbstractRestDocsTests;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.board.notice.NoticeBoardService;
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

@WebMvcTest(NoticeBoardController.class)
public class NoticeBoardControllerTest extends AbstractRestDocsTests {

    @MockBean
    private NoticeBoardService noticeBoardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_whenValidRequest_thenReturnNoticeBoardDto() throws Exception {
        // Given
        Long memberId = 1L;
        NoticeBoardCreateRequest request = TestBuilder.createNoticeBoardCreateRequest();
        NoticeBoardDto noticeBoardDto = TestBuilder.createNoticeBoardDto();
        given(noticeBoardService.create(Mockito.anyLong(), Mockito.any(NoticeBoardCreateRequest.class))).willReturn(noticeBoardDto);

        // When, Then
        try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
            mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

            mockMvc.perform(post("/api/boards/notice")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.title").value("Test Notice Title"))
                    .andDo(document("notice-board-controller/create",
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
    void update_whenValidRequest_thenReturnUpdatedNoticeBoardDto() throws Exception {
        // Given
        Long memberId = 1L;
        Long boardId = 1L;
        NoticeBoardUpdateRequest updateRequest = TestBuilder.createNoticeBoardUpdateRequest();
        NoticeBoardDto noticeBoardDto = TestBuilder.createUpdatedNoticeBoardDto();
        given(noticeBoardService.update(Mockito.anyLong(), Mockito.any(NoticeBoardUpdateRequest.class))).willReturn(noticeBoardDto);

        // When, Then
        try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
            mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

            mockMvc.perform(patch("/api/boards/notice/{id}", boardId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.title").value("Updated Notice Title"))
                    .andDo(document("notice-board-controller/update",
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
    void read_whenValidRequest_thenReturnNoticeBoardDto() throws Exception {
        // Given
        Long boardId = 1L;
        NoticeBoardDto noticeBoardDto = TestBuilder.createNoticeBoardDto();
        given(noticeBoardService.read(boardId)).willReturn(noticeBoardDto);

        // When, Then
        mockMvc.perform(get("/api/boards/notice/{id}", boardId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Notice Title"))
                .andDo(document("notice-board-controller/read",
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
    void readAll_whenValidRequest_thenReturnNoticeBoardDtoList() throws Exception {
        // Given
        Long clubId = 1L;
        List<NoticeBoardDto> noticeBoardDtos = List.of(TestBuilder.createNoticeBoardDto());
        given(noticeBoardService.readAll(clubId)).willReturn(noticeBoardDtos);

        // When, Then
        mockMvc.perform(get("/api/boards/notice/club/{clubId}", clubId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Test Notice Title"))
                .andDo(document("notice-board-controller/readAll",
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
        willDoNothing().given(noticeBoardService).delete(boardId);

        // When, Then
        mockMvc.perform(delete("/api/boards/notice/{id}", boardId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("notice-board-controller/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시물 ID")
                        )
                ));
    }
}

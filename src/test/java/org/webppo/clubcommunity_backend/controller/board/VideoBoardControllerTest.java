package org.webppo.clubcommunity_backend.controller.board;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.webppo.clubcommunity_backend.builder.TestBuilder;
import org.webppo.clubcommunity_backend.controller.board.video.VideoBoardController;
import org.webppo.clubcommunity_backend.dto.board.video.VideoBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.video.VideoBoardDto;
import org.webppo.clubcommunity_backend.dto.board.video.VideoBoardUpdateRequest;
import org.webppo.clubcommunity_backend.restdocs.AbstractRestDocsTests;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.board.video.VideoBoardService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VideoBoardController.class)
public class VideoBoardControllerTest extends AbstractRestDocsTests {

    @MockBean
    private VideoBoardService videoBoardService;

    @Test
    void create_whenValidRequest_thenReturnVideoBoardDto() throws Exception {
        // Given
        Long memberId = 1L;
        VideoBoardCreateRequest request = TestBuilder.createVideoBoardCreateRequest();
        VideoBoardDto videoBoardDto = TestBuilder.createVideoBoardDto();
        given(videoBoardService.create(Mockito.anyLong(), Mockito.any(VideoBoardCreateRequest.class))).willReturn(videoBoardDto);

        // When, Then
        try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
            mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

            mockMvc.perform(multipart("/api/boards/video")
                            .file((MockMultipartFile) request.getVideos().get(0))
                            .param("title", request.getTitle())
                            .param("clubId", request.getClubId().toString())
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.title").value("Test Video Title"))
                    .andDo(document("video-board-controller/create",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestParts(
                                    partWithName("videos").description("업로드할 비디오 파일 리스트")
                            ),
                            responseFields(
                                    fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                                    fieldWithPath("data.images").type(JsonFieldType.ARRAY).description("비디오 리스트"),
                                    fieldWithPath("data.images[].id").type(JsonFieldType.NUMBER).description("비디오 ID"),
                                    fieldWithPath("data.images[].uniqueName").type(JsonFieldType.STRING).description("비디오 고유 이름"),
                                    fieldWithPath("data.images[].originName").type(JsonFieldType.STRING).description("비디오 원본 이름")
                            )
                    ));
        }
    }

    @Test
    void update_whenValidRequest_thenReturnUpdatedVideoBoardDto() throws Exception {
        // Given
        Long memberId = 1L;
        Long boardId = 1L;
        VideoBoardUpdateRequest updateRequest = TestBuilder.createVideoBoardUpdateRequest();
        VideoBoardDto videoBoardDto = TestBuilder.createUpdatedVideoBoardDto();
        given(videoBoardService.update(Mockito.anyLong(), Mockito.any(VideoBoardUpdateRequest.class))).willReturn(videoBoardDto);

        // When, Then
        try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
            mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

            mockMvc.perform(multipart("/api/boards/video/{id}", boardId)
                            .file((MockMultipartFile) updateRequest.getAddedVideos().get(0))
                            .param("title", updateRequest.getTitle())
                            .param("deletedVideos", updateRequest.getDeletedVideos().get(0).toString())
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .accept(MediaType.APPLICATION_JSON)
                            .with(request -> {
                                request.setMethod("PATCH");
                                return request;
                            }))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.title").value("Updated Video Title"))
                    .andDo(document("video-board-controller/update",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            pathParameters(
                                    parameterWithName("id").description("게시물 ID")
                            ),
                            requestParts(
                                    partWithName("addedVideos").description("추가할 비디오 파일 리스트")
                            ),
                            responseFields(
                                    fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                                    fieldWithPath("data.images").type(JsonFieldType.ARRAY).description("비디오 리스트"),
                                    fieldWithPath("data.images[].id").type(JsonFieldType.NUMBER).description("비디오 ID"),
                                    fieldWithPath("data.images[].uniqueName").type(JsonFieldType.STRING).description("비디오 고유 이름"),
                                    fieldWithPath("data.images[].originName").type(JsonFieldType.STRING).description("비디오 원본 이름")
                            )
                    ));
        }
    }

    @Test
    void read_whenValidRequest_thenReturnVideoBoardDto() throws Exception {
        // Given
        Long boardId = 1L;
        VideoBoardDto videoBoardDto = TestBuilder.createVideoBoardDto();
        given(videoBoardService.read(boardId)).willReturn(videoBoardDto);

        // When, Then
        mockMvc.perform(get("/api/boards/video/{id}", boardId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Video Title"))
                .andDo(document("video-board-controller/read",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("data.images").type(JsonFieldType.ARRAY).description("비디오 리스트"),
                                fieldWithPath("data.images[].id").type(JsonFieldType.NUMBER).description("비디오 ID"),
                                fieldWithPath("data.images[].uniqueName").type(JsonFieldType.STRING).description("비디오 고유 이름"),
                                fieldWithPath("data.images[].originName").type(JsonFieldType.STRING).description("비디오 원본 이름")
                        )
                ));
    }

    @Test
    void readAll_whenValidRequest_thenReturnVideoBoardDtoList() throws Exception {
        // Given
        Long clubId = 1L;
        List<VideoBoardDto> videoBoardDtos = List.of(TestBuilder.createVideoBoardDto());
        given(videoBoardService.readAll(clubId)).willReturn(videoBoardDtos);

        // When, Then
        mockMvc.perform(get("/api/boards/video/club/{clubId}", clubId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Test Video Title"))
                .andDo(document("video-board-controller/readAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("clubId").description("클럽 ID")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("data[].images").type(JsonFieldType.ARRAY).description("비디오 리스트"),
                                fieldWithPath("data[].images[].id").type(JsonFieldType.NUMBER).description("비디오 ID"),
                                fieldWithPath("data[].images[].uniqueName").type(JsonFieldType.STRING).description("비디오 고유 이름"),
                                fieldWithPath("data[].images[].originName").type(JsonFieldType.STRING).description("비디오 원본 이름")
                        )
                ));
    }

    @Test
    void delete_whenValidRequest_thenReturnVoid() throws Exception {
        // Given
        Long boardId = 1L;
        willDoNothing().given(videoBoardService).delete(boardId);

        // When, Then
        mockMvc.perform(delete("/api/boards/video/{id}", boardId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("video-board-controller/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시물 ID")
                        )
                ));
    }
}

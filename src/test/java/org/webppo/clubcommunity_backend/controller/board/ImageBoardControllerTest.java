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
import org.webppo.clubcommunity_backend.controller.board.image.ImageBoardController;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardDto;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardUpdateRequest;
import org.webppo.clubcommunity_backend.restdocs.AbstractRestDocsTests;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.board.image.ImageBoardService;

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

@WebMvcTest(
        controllers = ImageBoardController.class
)
public class ImageBoardControllerTest extends AbstractRestDocsTests {

    @MockBean
    private ImageBoardService imageBoardService;

    @Test
    void create_whenValidRequest_thenReturnImageBoardDto() throws Exception {
        // Given
        Long memberId = 1L;
        ImageBoardCreateRequest request = TestBuilder.createImageBoardCreateRequest();
        ImageBoardDto imageBoardDto = TestBuilder.createImageBoardDto();
        given(imageBoardService.create(Mockito.anyLong(), Mockito.any(ImageBoardCreateRequest.class))).willReturn(imageBoardDto);

        // When, Then
        try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
            mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

            mockMvc.perform(multipart("/api/boards/image")
                            .file((MockMultipartFile) request.getImages().get(0))
                            .param("title", request.getTitle())
                            .param("clubId", request.getClubId().toString())
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.title").value("Test Title"))
                    .andDo(document("image-board-controller/create",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestParts(
                                    partWithName("images").description("업로드할 이미지 파일 리스트")
                            ),
                            responseFields(
                                    fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                                    fieldWithPath("data.images").type(JsonFieldType.ARRAY).description("이미지 리스트"),
                                    fieldWithPath("data.images[].id").type(JsonFieldType.NUMBER).description("이미지 ID"),
                                    fieldWithPath("data.images[].uniqueName").type(JsonFieldType.STRING).description("이미지 고유 이름"),
                                    fieldWithPath("data.images[].originName").type(JsonFieldType.STRING).description("이미지 원본 이름")
                            )
                    ));
        }
    }

    @Test
    void update_whenValidRequest_thenReturnUpdatedImageBoardDto() throws Exception {
        // Given
        Long memberId = 1L;
        Long boardId = 1L;
        ImageBoardUpdateRequest updateRequest = TestBuilder.createImageBoardUpdateRequest();
        ImageBoardDto imageBoardDto = TestBuilder.createUpdatedImageBoardDto();
        given(imageBoardService.update(Mockito.anyLong(), Mockito.any(ImageBoardUpdateRequest.class))).willReturn(imageBoardDto);

        // When, Then
        try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
            mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

            mockMvc.perform(multipart("/api/boards/image/{id}", boardId)
                            .file((MockMultipartFile) updateRequest.getAddedImages().get(0))
                            .param("title", updateRequest.getTitle())
                            .param("deletedImages", updateRequest.getDeletedImages().get(0).toString())
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .accept(MediaType.APPLICATION_JSON)
                            .with(request -> {
                                request.setMethod("PATCH");
                                return request;
                            }))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.title").value("Updated Title"))
                    .andDo(document("image-board-controller/update",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            pathParameters(
                                    parameterWithName("id").description("게시물 ID")
                            ),
                            requestParts(
                                    partWithName("addedImages").description("추가할 이미지 파일 리스트")
                            ),
                            responseFields(
                                    fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                                    fieldWithPath("data.images").type(JsonFieldType.ARRAY).description("이미지 리스트"),
                                    fieldWithPath("data.images[].id").type(JsonFieldType.NUMBER).description("이미지 ID"),
                                    fieldWithPath("data.images[].uniqueName").type(JsonFieldType.STRING).description("이미지 고유 이름"),
                                    fieldWithPath("data.images[].originName").type(JsonFieldType.STRING).description("이미지 원본 이름")
                            )
                    ));
        }
    }

    @Test
    void read_whenValidRequest_thenReturnImageBoardDto() throws Exception {
        // Given
        Long boardId = 1L;
        ImageBoardDto imageBoardDto = TestBuilder.createImageBoardDto();
        given(imageBoardService.read(boardId)).willReturn(imageBoardDto);

        // When, Then
        mockMvc.perform(get("/api/boards/image/{id}", boardId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Title"))
                .andDo(document("image-board-controller/read",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("data.images").type(JsonFieldType.ARRAY).description("이미지 리스트"),
                                fieldWithPath("data.images[].id").type(JsonFieldType.NUMBER).description("이미지 ID"),
                                fieldWithPath("data.images[].uniqueName").type(JsonFieldType.STRING).description("이미지 고유 이름"),
                                fieldWithPath("data.images[].originName").type(JsonFieldType.STRING).description("이미지 원본 이름")
                        )
                ));
    }

    @Test
    void readAll_whenValidRequest_thenReturnImageBoardDtoList() throws Exception {
        // Given
        Long clubId = 1L;
        List<ImageBoardDto> imageBoardDtos = List.of(TestBuilder.createImageBoardDto());
        given(imageBoardService.readAll(clubId)).willReturn(imageBoardDtos);

        // When, Then
        mockMvc.perform(get("/api/boards/image/club/{clubId}", clubId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Test Title"))
                .andDo(document("image-board-controller/readAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("clubId").description("클럽 ID")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("data[].images").type(JsonFieldType.ARRAY).description("이미지 리스트"),
                                fieldWithPath("data[].images[].id").type(JsonFieldType.NUMBER).description("이미지 ID"),
                                fieldWithPath("data[].images[].uniqueName").type(JsonFieldType.STRING).description("이미지 고유 이름"),
                                fieldWithPath("data[].images[].originName").type(JsonFieldType.STRING).description("이미지 원본 이름")
                        )
                ));
    }

    @Test
    void delete_whenValidRequest_thenReturnVoid() throws Exception {
        // Given
        Long boardId = 1L;
        willDoNothing().given(imageBoardService).delete(boardId);

        // When, Then
        mockMvc.perform(delete("/api/boards/image/{id}", boardId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("image-board-controller/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시물 ID")
                        )
                ));
    }
}

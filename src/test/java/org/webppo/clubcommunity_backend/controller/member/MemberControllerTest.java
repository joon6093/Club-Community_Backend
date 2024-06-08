package org.webppo.clubcommunity_backend.controller.member;

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
import org.webppo.clubcommunity_backend.dto.member.MemberDto;
import org.webppo.clubcommunity_backend.dto.member.MemberSignupRequest;
import org.webppo.clubcommunity_backend.dto.member.MemberUpdateRequest;
import org.webppo.clubcommunity_backend.restdocs.AbstractRestDocsTests;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.member.MemberService;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = MemberController.class
)
public class MemberControllerTest extends AbstractRestDocsTests {

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signup_whenValidRequest_thenReturnMemberDto() throws Exception {
        // Given
        MemberSignupRequest request = TestBuilder.createMemberSignupRequest();
        MemberDto memberDto = TestBuilder.createMemberDto();
        given(memberService.register(request)).willReturn(memberDto);

        // When, Then
        mockMvc.perform(post("/api/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("testuser"))
                .andDo(document("member-controller/signup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").description("회원 이름"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("username").description("사용자 이름"),
                                fieldWithPath("profileImage").description("프로필 이미지 URL"),
                                fieldWithPath("birthDate").description("생년월일"),
                                fieldWithPath("gender").description("성별 (M 또는 F)"),
                                fieldWithPath("department").description("학과"),
                                fieldWithPath("studentId").description("학번"),
                                fieldWithPath("phoneNumber").description("전화번호"),
                                fieldWithPath("email").description("이메일 주소")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원 ID"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data.username").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                fieldWithPath("data.role").type(JsonFieldType.STRING).description("사용자 역할"),
                                fieldWithPath("data.birthDate").type(JsonFieldType.STRING).description("생년월일"),
                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별 (M 또는 F)"),
                                fieldWithPath("data.department").type(JsonFieldType.STRING).description("학과"),
                                fieldWithPath("data.studentId").type(JsonFieldType.STRING).description("학번"),
                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("전화번호"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일 주소"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성일시"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정일시")
                        )
                ));
    }

    @Test
    void update_whenValidRequest_thenReturnUpdatedMemberDto() throws Exception {
        // Given
        Long memberId = 1L;
        MemberUpdateRequest request = TestBuilder.createMemberUpdateRequest();
        MemberDto memberDto = TestBuilder.createUpdatedMemberDto(); // Updated MemberDto with new email
        given(memberService.update(memberId, request)).willReturn(memberDto);

        // When, Then
        try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
            mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

            mockMvc.perform(patch("/api/members/pending")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.email").value("newemail@example.com"))
                    .andDo(document("member-controller/update",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                    fieldWithPath("birthDate").description("생년월일"),
                                    fieldWithPath("gender").description("성별 (M 또는 F)"),
                                    fieldWithPath("department").description("학과"),
                                    fieldWithPath("studentId").description("학번"),
                                    fieldWithPath("phoneNumber").description("전화번호"),
                                    fieldWithPath("email").description("이메일 주소")
                            ),
                            responseFields(
                                    fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원 ID"),
                                    fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                    fieldWithPath("data.username").type(JsonFieldType.STRING).description("사용자 이름"),
                                    fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                    fieldWithPath("data.role").type(JsonFieldType.STRING).description("사용자 역할"),
                                    fieldWithPath("data.birthDate").type(JsonFieldType.STRING).description("생년월일"),
                                    fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별 (M 또는 F)"),
                                    fieldWithPath("data.department").type(JsonFieldType.STRING).description("학과"),
                                    fieldWithPath("data.studentId").type(JsonFieldType.STRING).description("학번"),
                                    fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("전화번호"),
                                    fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일 주소"),
                                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성일시"),
                                    fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정일시")
                            )
                    ));
        }
    }

    @Test
    void readMe_whenAuthenticated_thenReturnMemberDto() throws Exception {
        // Given
        Long memberId = 1L;
        MemberDto memberDto = TestBuilder.createMemberDto();
        given(memberService.read(memberId)).willReturn(memberDto);

        // When, Then
        try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
            mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

            mockMvc.perform(get("/api/members/me")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.username").value("testuser"))
                    .andDo(document("member-controller/readMe",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            responseFields(
                                    fieldWithPath("success").type(JsonFieldType.STRING).description("성공 여부"),
                                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원 ID"),
                                    fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                    fieldWithPath("data.username").type(JsonFieldType.STRING).description("사용자 이름"),
                                    fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                    fieldWithPath("data.role").type(JsonFieldType.STRING).description("사용자 역할"),
                                    fieldWithPath("data.birthDate").type(JsonFieldType.STRING).description("생년월일"),
                                    fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별 (M 또는 F)"),
                                    fieldWithPath("data.department").type(JsonFieldType.STRING).description("학과"),
                                    fieldWithPath("data.studentId").type(JsonFieldType.STRING).description("학번"),
                                    fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("전화번호"),
                                    fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일 주소"),
                                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성일시"),
                                    fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정일시")
                            )
                    ));
        }
    }
}

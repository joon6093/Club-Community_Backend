package org.webppo.clubcommunity_backend.controller.club;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.webppo.clubcommunity_backend.builder.TestBuilder;
import org.webppo.clubcommunity_backend.dto.club.ClubDto;
import org.webppo.clubcommunity_backend.dto.club.ClubUpdateRequest;
import org.webppo.clubcommunity_backend.dto.club.IsMasterDto;
import org.webppo.clubcommunity_backend.dto.member.MemberDto;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;
import org.webppo.clubcommunity_backend.restdocs.AbstractRestDocsTests;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.security.SecurityConfig;
import org.webppo.clubcommunity_backend.service.club.ClubService;

@WebMvcTest(
	controllers = ClubController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class ClubControllerTest extends AbstractRestDocsTests{

	@MockBean
	private ClubService clubService;

	@Test
	void isMaster_whenUserIsMaster_thenReturnTrue() throws Exception {
		// Given
		Long clubId = 1L;
		Long memberId = 1L;
		Boolean isMaster = true;
		IsMasterDto isMasterDto = new IsMasterDto(isMaster);
		given(clubService.isMaster(memberId, clubId)).willReturn(isMaster);
		try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
			mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

			// When, Then
			mockMvc.perform(get("/api/clubs/{clubId}/is-master", clubId)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.isMaster").value(true));
		}
	}

	@Test
	void findById_whenClubExists_thenReturnClubDto() throws Exception {
		// Given
		Long clubId = 1L;
		Member member = TestBuilder.createMember();
		Club club = TestBuilder.createClub(member);
		ClubDto clubDto = TestBuilder.createClubDto(club);
		given(clubService.findById(clubId)).willReturn(clubDto);

		// When, Then
		mockMvc.perform(get("/api/clubs/{clubId}", clubId)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	void findAll_whenClubsExist_thenReturnClubDtoList() throws Exception {
		// Given
		Member member = TestBuilder.createMember();
		Club club1 = TestBuilder.createClub(member);
		Club club2 = TestBuilder.createClub(member);
		List<ClubDto> clubDtoList = List.of(
			TestBuilder.createClubDto(club1),
			TestBuilder.createClubDto(club2)
		);
		given(clubService.findAll()).willReturn(clubDtoList);

		// When, Then
		mockMvc.perform(get("/api/clubs")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	void updateClub_whenUserIsMaster_thenReturnCreated() throws Exception {
		// Given
		Long clubId = 1L;
		Long memberId = 1L;
		ClubUpdateRequest request = TestBuilder.createClubUpdateRequest();
		doNothing().when(clubService).updateClub(any(ClubUpdateRequest.class), eq(memberId), eq(clubId));
		try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
			mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

			// When, Then
			mockMvc.perform(multipart("/api/clubs/{clubId}/update", clubId)
					.file((MockMultipartFile)request.getClubPhoto())
					.param("clubName", request.getClubName())
					.param("clubIntroduction", request.getClubIntroduction())
					.param("clubHistory", request.getClubHistory())
					.param("meetingTime", request.getMeetingTime())
					.param("president", request.getPresident())
					.param("vicePresident", request.getVicePresident())
					.param("secretary", request.getSecretary())
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.accept(MediaType.APPLICATION_JSON)
					.with(rq -> {
						rq.setMethod("PATCH");
						return rq;
					}))
				.andExpect(status().isCreated());
		}
	}

	@Test
	void findMemberId() throws Exception {
		// Arrange
		Long memberId = 1L;
		Member member = TestBuilder.createMember();
		Club club = TestBuilder.createClub(member);
		ClubDto clubDto1 = TestBuilder.createClubDto(club);
		ClubDto clubDto2 = TestBuilder.createClubDto(club);
		List<ClubDto> clubDtos = Arrays.asList(
			clubDto1,
			clubDto2
		);
		given(clubService.findMemberId(memberId)).willReturn(clubDtos);

		try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
			mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

			// Act
			mockMvc.perform(get("/api/clubs/me")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		}
	}

	@Test
	void findClubMembers() throws Exception {
		// Arrange
		Long clubId = 1L;
		Long memberId = 2L;
		Member member = TestBuilder.createMember();
		List<MemberDto> memberDtos = Arrays.asList(
			MemberDto.from(member),
			MemberDto.from(member)
		);
		given(clubService.findClubMembers(clubId, memberId)).willReturn(memberDtos);

		try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
			mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(memberId);

			// Act
			mockMvc.perform(get("/api/clubs/{clubId}/members", clubId)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		}
	}

	@Test
	void deleteClubMember() throws Exception {
		// Arrange
		Long clubId = 1L;
		Long clubMemberId = 2L;
		Long masterId = 2L;

		willDoNothing().given(clubService).deleteClubMember(clubId, clubMemberId, masterId);

		try (MockedStatic<PrincipalHandler> mockedPrincipalHandler = Mockito.mockStatic(PrincipalHandler.class)) {
			mockedPrincipalHandler.when(PrincipalHandler::extractId).thenReturn(masterId);

			// Act
			mockMvc.perform(delete("/api/clubs/{clubId}/members/{memberId}", clubId, clubMemberId)
				.contentType(MediaType.APPLICATION_JSON));

			then(clubService).should().deleteClubMember(clubId, clubMemberId, masterId);
		}
	}
}

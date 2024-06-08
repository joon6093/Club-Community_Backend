package org.webppo.clubcommunity_backend.controller.club;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.webppo.clubcommunity_backend.builder.TestBuilder;
import org.webppo.clubcommunity_backend.dto.club.ClubDto;
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


}

package org.webppo.clubcommunity_backend.builder;

import org.webppo.clubcommunity_backend.dto.club.ClubDto;
import org.webppo.clubcommunity_backend.dto.member.MemberDto;
import org.webppo.clubcommunity_backend.dto.member.MemberSignupRequest;
import org.webppo.clubcommunity_backend.dto.member.MemberUpdateRequest;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;

import java.time.LocalDateTime;

public class TestBuilder {

	public static Member createMember() {
		return Member.builder()
			.name("test")
			.password("test")
			.username("test")
			.profileImage("test")
			.birthDate(LocalDateTime.now())
			.gender("test")
			.department("test")
			.studentId("test")
			.phoneNumber("test")
			.email("test")
			.role(RoleType.ROLE_USER)
			.build();
	}

	public static MemberDto createMemberDto() {
		return new MemberDto(
				1L,
				"Test User",
				"testuser",
				"testProfileImage",
				RoleType.ROLE_USER,
				LocalDateTime.now().minusYears(20), // Make sure it's in the past
				"M",
				"Computer Science",
				"123456",
				"010-1234-5678",
				"test@example.com",
				LocalDateTime.now(),
				LocalDateTime.now()
		);
	}

	public static MemberSignupRequest createMemberSignupRequest() {
		return new MemberSignupRequest(
				"Test User",
				"testpassword",
				"testuser",
				"testProfileImage",
				LocalDateTime.now().minusYears(20), // Make sure it's in the past
				"M",
				"Computer Science",
				"123456",
				"010-1234-5678",
				"test@example.com"
		);
	}

	public static MemberUpdateRequest createMemberUpdateRequest() {
		return new MemberUpdateRequest(
				LocalDateTime.now().minusYears(20), // Make sure it's in the past
				"M",
				"Computer Science",
				"123456",
				"010-1234-5678",
				"newemail@example.com"
		);
	}

	public static MemberDto createUpdatedMemberDto() {
		return new MemberDto(
				1L,
				"Test User",
				"testuser",
				"testProfileImage",
				RoleType.ROLE_USER,
				LocalDateTime.now().minusYears(20), // Make sure it's in the past
				"M",
				"Computer Science",
				"123456",
				"010-1234-5678",
				"newemail@example.com",
				LocalDateTime.now(),
				LocalDateTime.now()
		);
	}

	public static Club createClub(Member member) {
		return new Club("ClubName", member);
	}

	public static ClubDto createClubDto(Club club) {
		return new ClubDto(club);
	}
}

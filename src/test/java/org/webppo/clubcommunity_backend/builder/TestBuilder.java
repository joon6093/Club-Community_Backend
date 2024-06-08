package org.webppo.clubcommunity_backend.builder;

import java.time.LocalDateTime;

import org.webppo.clubcommunity_backend.dto.club.ClubDto;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;

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

	public static Club createClub(Member member) {
		return new Club("ClubName", member);
	}

	public static ClubDto createClubDto(Club club) {
		return new ClubDto(club);
	}
}

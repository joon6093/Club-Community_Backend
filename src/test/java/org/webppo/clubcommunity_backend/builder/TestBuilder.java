package org.webppo.clubcommunity_backend.builder;

import org.springframework.mock.web.MockMultipartFile;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardDto;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardUpdateRequest;
import org.webppo.clubcommunity_backend.dto.club.ClubDto;
import org.webppo.clubcommunity_backend.dto.club.ClubUpdateRequest;
import org.webppo.clubcommunity_backend.dto.member.MemberDto;
import org.webppo.clubcommunity_backend.dto.member.MemberSignupRequest;
import org.webppo.clubcommunity_backend.dto.member.MemberUpdateRequest;
import org.webppo.clubcommunity_backend.entity.board.image.Image;
import org.webppo.clubcommunity_backend.entity.board.image.ImageBoard;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;

import java.time.LocalDateTime;
import java.util.List;

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
				LocalDateTime.now().minusYears(20),
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
				LocalDateTime.now().minusYears(20),
				"M",
				"Computer Science",
				"123456",
				"010-1234-5678",
				"test@example.com"
		);
	}

	public static MemberUpdateRequest createMemberUpdateRequest() {
		return new MemberUpdateRequest(
				LocalDateTime.now().minusYears(20),
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

	public static ImageBoard createImageBoard(Member member) {
		ImageBoard imageBoard = ImageBoard.builder()
				.title("Test Title")
				.club(createClub(member))
				.member(member)
				.build();
		Image image = new Image("testImage.jpg");
		image.setImageBoard(imageBoard);
		imageBoard.addImages(List.of(image));
		return imageBoard;
	}

	public static ImageBoardDto createImageBoardDto() {
		return new ImageBoardDto(
				1L,
				"Test Title",
				List.of(new ImageBoardDto.ImageDto(1L, "testImage.jpg", "testImage.jpg"))
		);
	}

	public static ImageBoardDto createUpdatedImageBoardDto() {
		return new ImageBoardDto(
				1L,
				"Updated Title",
				List.of(new ImageBoardDto.ImageDto(1L, "testImage.jpg", "testImage.jpg"))
		);
	}

	public static MockMultipartFile createMockMultipartFile() {
		return new MockMultipartFile("image", "image.jpg", "image/jpeg", "test image".getBytes());
	}

	public static ImageBoardCreateRequest createImageBoardCreateRequest() {
		return new ImageBoardCreateRequest("Test Title", 1L, List.of(new MockMultipartFile("images", "image.jpg", "image/jpeg", "test image".getBytes())));
	}

	public static ImageBoardUpdateRequest createImageBoardUpdateRequest() {
		return new ImageBoardUpdateRequest("Updated Title", List.of(new MockMultipartFile("addedImages", "image.jpg", "image/jpeg", "test image".getBytes())), List.of(1L));
	}

	public static ClubUpdateRequest createClubUpdateRequest() {
		return new ClubUpdateRequest(
			"Club Name",
			"Club Introduction",
			"Club History",
			new MockMultipartFile("clubPhoto", "clubPhoto.jpg", "image/jpeg", new byte[0]),
			"Meeting Time",
			"President",
			"Vice President",
			"Secretary"
		);
	}
}

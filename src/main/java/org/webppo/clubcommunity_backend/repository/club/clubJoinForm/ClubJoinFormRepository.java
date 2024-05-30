package org.webppo.clubcommunity_backend.repository.club.clubJoinForm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.webppo.clubcommunity_backend.dto.club.clubJoinForm.ClubJoinFormResponse;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.club.clubJoinForm.ClubJoinForm;

import java.util.List;

@Repository
public interface ClubJoinFormRepository extends JpaRepository<ClubJoinForm, Long> {
    List<ClubJoinForm> findByClub(Club club);
}

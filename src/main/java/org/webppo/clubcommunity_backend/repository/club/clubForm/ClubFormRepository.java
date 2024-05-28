package org.webppo.clubcommunity_backend.repository.club.clubForm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.webppo.clubcommunity_backend.entity.club.clubForm.ClubForm;

import java.util.List;


@Repository
public interface ClubFormRepository extends JpaRepository<ClubForm, Long> {
    List<ClubForm> findByMemberId(Long memberId);
}

package org.webppo.clubcommunity_backend.repository.club;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.webppo.clubcommunity_backend.entity.club.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club,Long> {
}

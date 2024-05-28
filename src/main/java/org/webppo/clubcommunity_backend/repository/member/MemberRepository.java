package org.webppo.clubcommunity_backend.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.webppo.clubcommunity_backend.dto.member.MemberDto;
import org.webppo.clubcommunity_backend.entity.member.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    @Query("SELECT new org.webppo.clubcommunity_backend.dto.member.MemberDto(" +
            "m.id, m.name, m.username, m.profileImage, m.role, m.birthDate, m.gender, " +
            "m.department, m.studentId, m.phoneNumber, m.email, m.createdAt, m.modifiedAt) " +
            "FROM Member m WHERE m.id = :id")
    Optional<MemberDto> findDtoById(@Param("id") Long id);
}

package org.webppo.clubcommunity_backend.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.webppo.clubcommunity_backend.entity.member.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    @Query("SELECT m FROM Member m JOIN FETCH m.role WHERE m.name = :name")
    Optional<Member> findByNameWithRoles(@Param("name") String name);
    @Query("SELECT m FROM Member m JOIN FETCH m.role WHERE m.id = :id")
    Optional<Member> findByIdWithRoles(@Param("id") Long id);
}

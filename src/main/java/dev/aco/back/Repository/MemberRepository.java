package dev.aco.back.Repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import dev.aco.back.Entity.User.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByEmail(String email);
    
    Boolean existsByMobile(String mobile); 
    
    Optional<Member> findByEmail(String email);
    
    @Modifying
    @Query("UPDATE Member mb set mb.logged=:logged where mb.memberId =:memberId")
    void loggedMember(Long memberId, boolean logged);
}

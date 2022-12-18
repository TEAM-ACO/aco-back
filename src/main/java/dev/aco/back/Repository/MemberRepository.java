package dev.aco.back.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import dev.aco.back.Entity.User.Member;
import jakarta.transaction.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByMobile(String mobile);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberId(Long memberId);

    @Modifying
    @Query("UPDATE Member mb set mb.logged=:logged where mb.memberId =:memberId")
    void loggedMember(Long memberId, boolean logged);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m set m.password =:password where m.memberId=:memberId")
    Long changePassbyMemberId(Long memberId, String password);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m set m.nickname =:nickname where m.memberId=:memberId")
    void changeNicknamebyMemberId(Long memberId, String nickname);

    // --------------------User Image-----------------------------
    @Query("select userimg from Member m where m.memberId =:memberId")
    Optional<String> getUserImgByMemberId(Long memberId);

    @Modifying
    @Query("UPDATE Member mb set mb.userimg=:userimg where mb.memberId =:memberId")
    void updateUserImg(String userimg, Long memberId);

}

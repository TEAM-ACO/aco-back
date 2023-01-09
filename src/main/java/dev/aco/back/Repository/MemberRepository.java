package dev.aco.back.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import dev.aco.back.Entity.User.Member;
import jakarta.transaction.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("""
            SELECT mb.memberId as memberId, mb.nickname as nickname, mb.createdDateTime as joindate, mb.email as email 
            FROM Member mb 
            """)
    Page<getRecentMember> findAllRecentMember(Pageable pageable);

    Boolean existsByEmail(String email);

    Boolean existsByMobile(String mobile);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberId(Long memberId);

    @Modifying
    @Query("UPDATE Member mb set mb.logged=:logged where mb.memberId =:memberId")
    void loggedMember(Long memberId, boolean logged);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member mb set mb.password =:password where mb.memberId=:memberId")
    Long changePassbyMemberId(Long memberId, String password);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m set m.nickname =:nickname where m.memberId=:memberId")
    void changeNicknamebyMemberId(Long memberId, String nickname);

    // --------------------Find Password--------------------------
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member mb set mb.password =:password where mb.email=:email")
    Integer changeFindPassbyEmail(String email, String password);

    // --------------------User Image-----------------------------
    @Query("select userimg from Member m where m.memberId =:memberId")
    Optional<String> getUserImgByMemberId(Long memberId);

    @Modifying
    @Query("UPDATE Member mb set mb.userimg=:userimg where mb.memberId =:memberId")
    void updateUserImg(String userimg, Long memberId);


    public interface getRecentMember{
        Long getMemberId();
        String getNickname();
        String getEmail();
        LocalDateTime getJoindate();
    }
}

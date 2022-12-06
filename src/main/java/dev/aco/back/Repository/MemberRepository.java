package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.aco.back.Entity.User.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  Boolean existsByEmail(String email);

  Boolean existsByMobile(String mobile);
}

package me.jaden.redisstudy.member.repository;

import me.jaden.redisstudy.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

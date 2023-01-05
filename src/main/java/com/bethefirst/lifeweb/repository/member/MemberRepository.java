package com.bethefirst.lifeweb.repository.member;

import com.bethefirst.lifeweb.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}

package com.bethefirst.lifeweb.repository.member;

import com.bethefirst.lifeweb.dto.member.request.MemberSearchRequirements;
import com.bethefirst.lifeweb.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryQueryDsl {
    /**검색 조건에 따른 회원 리스트 조회 */
    Page<Member> findAllBySearchRequirements(MemberSearchRequirements searchRequirements, Pageable pageable);
}

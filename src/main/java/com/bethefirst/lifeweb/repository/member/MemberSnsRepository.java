package com.bethefirst.lifeweb.repository.member;

import com.bethefirst.lifeweb.entity.member.MemberSns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSnsRepository extends JpaRepository<MemberSns, Long> {
    boolean existsBySnsUrl(String snsUrl);

    Optional<MemberSns> findBySns_Name(String snsName);
}

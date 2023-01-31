package com.bethefirst.lifeweb.repository.member;

import com.bethefirst.lifeweb.entity.member.Sns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnsRepository extends JpaRepository<Sns, Long> {

}

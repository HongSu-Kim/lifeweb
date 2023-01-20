package com.bethefirst.lifeweb.repository.member;

import com.bethefirst.lifeweb.entity.member.Sns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SnsRepository extends JpaRepository<Sns, Long> {
	List<Sns> findAllByNameIn(List<String> snsName);
	Optional<Sns> findByName(String snsName);
}

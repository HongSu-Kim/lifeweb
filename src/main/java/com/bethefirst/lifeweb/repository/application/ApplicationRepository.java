package com.bethefirst.lifeweb.repository.application;

import com.bethefirst.lifeweb.entity.application.Application;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

	@Override
	@EntityGraph(attributePaths = { "applicationQuestionList" })
	Optional<Application> findById(Long applicationId);

}

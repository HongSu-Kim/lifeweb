package com.bethefirst.lifeweb.repository.application;

import com.bethefirst.lifeweb.entity.application.ApplicantAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationAnswerRepository extends JpaRepository<ApplicantAnswer, Long> {
}
package com.bethefirst.lifeweb.repository.application;

import com.bethefirst.lifeweb.entity.application.ApplicationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationQuestionRepository extends JpaRepository<ApplicationQuestion, Long> {
}
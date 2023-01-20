package com.bethefirst.lifeweb.repository.campaign;

import com.bethefirst.lifeweb.entity.campaign.ApplicationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationQuestionRepository extends JpaRepository<ApplicationQuestion, Long> {
}
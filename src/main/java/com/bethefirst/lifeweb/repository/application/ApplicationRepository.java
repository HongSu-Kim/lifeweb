package com.bethefirst.lifeweb.repository.application;

import com.bethefirst.lifeweb.entity.application.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

}

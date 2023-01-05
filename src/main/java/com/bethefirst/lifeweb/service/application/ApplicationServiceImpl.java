package com.bethefirst.lifeweb.service.application;

import com.bethefirst.lifeweb.repository.application.ApplicationRepository;
import com.bethefirst.lifeweb.repository.campaign.CampaignRepository;
import com.bethefirst.lifeweb.service.application.interfaces.ApplicationService;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ApplicationServiceImpl implements CampaignService {

	private final ApplicationRepository applicationRepository;

}

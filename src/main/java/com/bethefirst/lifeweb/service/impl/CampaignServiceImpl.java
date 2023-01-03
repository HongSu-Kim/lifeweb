package com.bethefirst.lifeweb.service.impl;

import com.bethefirst.lifeweb.repository.CampaignRepository;
import com.bethefirst.lifeweb.service.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class CampaignServiceImpl implements CampaignService {

	private final CampaignRepository campaignRepository;

}

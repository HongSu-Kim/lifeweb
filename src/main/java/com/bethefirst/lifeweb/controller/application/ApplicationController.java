package com.bethefirst.lifeweb.controller.application;

import com.bethefirst.lifeweb.service.application.interfaces.ApplicationService;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("campaign")
@Slf4j
public class ApplicationController {

	private final ApplicationService applicationService;

}

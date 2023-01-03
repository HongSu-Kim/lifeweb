package com.bethefirst.lifeweb.controller;

import com.bethefirst.lifeweb.service.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("campaign")
@Slf4j
public class CampaignController {

	private final CampaignService campaignService;

}

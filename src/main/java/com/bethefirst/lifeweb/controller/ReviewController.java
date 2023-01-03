package com.bethefirst.lifeweb.controller;

import com.bethefirst.lifeweb.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("review")
@Slf4j
public class ReviewController {

	private final ReviewService reviewService;

}

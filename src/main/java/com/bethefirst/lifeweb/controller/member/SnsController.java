package com.bethefirst.lifeweb.controller.member;

import com.bethefirst.lifeweb.dto.member.request.SnsCreateDto;
import com.bethefirst.lifeweb.service.member.interfaces.SnsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sns")
@RequiredArgsConstructor
@Slf4j
public class SnsController {

    private final SnsService snsService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void create(@Valid @RequestBody SnsCreateDto snsCreateDto){
        snsService.createSns(snsCreateDto);
    }
}

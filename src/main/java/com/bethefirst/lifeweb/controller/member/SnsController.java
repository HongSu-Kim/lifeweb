package com.bethefirst.lifeweb.controller.member;

import com.bethefirst.lifeweb.dto.member.response.SnsDto;
import com.bethefirst.lifeweb.dto.member.request.CreateSnsDto;
import com.bethefirst.lifeweb.dto.member.request.UpdateSnsDto;
import com.bethefirst.lifeweb.service.member.interfaces.SnsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sns")
@RequiredArgsConstructor
@Slf4j
public class SnsController {

    private final SnsService snsService;

    /** SNS 등록 */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void create(@Valid @RequestBody CreateSnsDto createSnsDto){
        snsService.createSns(createSnsDto);


    }

    /** SNS 수정 */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{snsId}")
    public void update(@PathVariable Long snsId,
                       @Valid @RequestBody UpdateSnsDto updateSnsDto){
        snsService.updateSns(updateSnsDto, snsId);
    }


    /** SNS 삭제 */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{snsId}")
    public void delete(@PathVariable Long snsId){
        snsService.deleteSns(snsId);
    }

    /** SNS 단건조회 */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{snsId}")
    public SnsDto detail(@PathVariable Long snsId){
        return snsService.getSns(snsId);
    }

    /** SNS 전체조회 */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<SnsDto> list(){
        return snsService.getSnsList();
    }









}

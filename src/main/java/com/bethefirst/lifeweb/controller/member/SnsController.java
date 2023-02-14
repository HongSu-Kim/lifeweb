package com.bethefirst.lifeweb.controller.member;

import com.bethefirst.lifeweb.dto.member.response.SnsDto;
import com.bethefirst.lifeweb.dto.member.request.CreateSnsDto;
import com.bethefirst.lifeweb.dto.member.request.UpdateSnsDto;
import com.bethefirst.lifeweb.service.member.interfaces.SnsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sns")
@RequiredArgsConstructor
@Slf4j
public class SnsController {

    private final SnsService snsService;

    /** SNS 등록 */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateSnsDto createSnsDto){
        Long saveSnsId = snsService.createSns(createSnsDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/sns/" + saveSnsId));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);

    }

    /** SNS 수정 */
    @PutMapping("/{snsId}")
    public ResponseEntity<?> update(@PathVariable Long snsId,
                       @Valid @RequestBody UpdateSnsDto updateSnsDto){
        snsService.updateSns(updateSnsDto, snsId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/sns/" + snsId));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    /** SNS 삭제 */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{snsId}")
    public ResponseEntity<?> delete(@PathVariable Long snsId){
        snsService.deleteSns(snsId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/sns/" + snsId));
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
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

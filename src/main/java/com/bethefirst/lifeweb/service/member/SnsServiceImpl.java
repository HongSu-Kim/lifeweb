package com.bethefirst.lifeweb.service.member;

import com.bethefirst.lifeweb.dto.member.SnsDto;
import com.bethefirst.lifeweb.dto.member.request.SnsCreateDto;
import com.bethefirst.lifeweb.dto.member.request.SnsUpdateDto;
import com.bethefirst.lifeweb.entity.member.Sns;
import com.bethefirst.lifeweb.repository.member.SnsRepository;
import com.bethefirst.lifeweb.service.member.interfaces.SnsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SnsServiceImpl implements SnsService {

    private final  SnsRepository snsRepository;

    /** SNS 등록 */
    @Override
    public void createSns(SnsCreateDto snsCreateDto) {

        Sns sns = snsCreateDto.createSns();
        snsRepository.save(sns);

    }

    /** SNS 수정 */
    @Override
    public void updateSns(SnsUpdateDto snsUpdateDto, Long snsId) {
        Sns sns = snsRepository.findById(snsId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 snsId 입니다. " + String.valueOf(snsId)));

        snsUpdateDto.updateSns(sns);
    }

    /** SNS 삭제 */
    @Override
    public void deleteSns(Long snsId) {
        Sns sns = snsRepository.findById(snsId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 snsId 입니다. " + String.valueOf(snsId)));

        snsRepository.delete(sns);
    }


    /** SNS 단건조회 */
    @Override
    public SnsDto getSns(Long snsId) {
        Sns sns = snsRepository.findById(snsId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 snsId 입니다. " + String.valueOf(snsId)));

        return new SnsDto(sns);

    }




}

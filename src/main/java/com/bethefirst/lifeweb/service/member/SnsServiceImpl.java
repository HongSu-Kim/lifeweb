package com.bethefirst.lifeweb.service.member;

import com.bethefirst.lifeweb.dto.member.response.SnsDto;
import com.bethefirst.lifeweb.dto.member.request.CreateSnsDto;
import com.bethefirst.lifeweb.dto.member.request.UpdateSnsDto;
import com.bethefirst.lifeweb.entity.member.Sns;
import com.bethefirst.lifeweb.repository.member.SnsRepository;
import com.bethefirst.lifeweb.service.member.interfaces.SnsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SnsServiceImpl implements SnsService {

    private final  SnsRepository snsRepository;

    /** SNS 등록 */
    @Override
    public Long createSns(CreateSnsDto createSnsDto) {

        Sns sns = createSnsDto.createSns();
        return snsRepository.save(sns).getId();


    }

    /** SNS 수정 */
    @Override
    public void updateSns(UpdateSnsDto updateSnsDto, Long snsId) {
        Sns sns = snsRepository.findById(snsId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 snsId 입니다. " + String.valueOf(snsId)));

        updateSnsDto.updateSns(sns);
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

    /** SNS 전체조회 */
    @Override
    public List<SnsDto> getSnsList() {
        return snsRepository.findAll().stream().map(SnsDto::new).collect(Collectors.toList());
    }


}

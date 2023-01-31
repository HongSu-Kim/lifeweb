package com.bethefirst.lifeweb.service.member;

import com.bethefirst.lifeweb.dto.member.request.SnsCreateDto;
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
}

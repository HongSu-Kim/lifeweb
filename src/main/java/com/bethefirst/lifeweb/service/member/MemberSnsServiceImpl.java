package com.bethefirst.lifeweb.service.member;

import com.bethefirst.lifeweb.dto.member.request.CreateMemberSnsDto;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.member.MemberSns;
import com.bethefirst.lifeweb.entity.member.Sns;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import com.bethefirst.lifeweb.repository.member.MemberSnsRepository;
import com.bethefirst.lifeweb.repository.member.SnsRepository;
import com.bethefirst.lifeweb.service.member.interfaces.MemberSnsService;
import com.bethefirst.lifeweb.util.UrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberSnsServiceImpl implements MemberSnsService {

    private final MemberRepository memberRepository;
    private final MemberSnsRepository memberSnsRepository;
    private final SnsRepository snsRepository;
    private final UrlUtil urlUtil;

    /** 회원 SNS 등록 **/
    @Override
    public void createMemberSns(CreateMemberSnsDto createMemberSnsDto) {
        //회원 유효성 검사
        Member member = memberRepository.findById(createMemberSnsDto.getMemberId()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 회원입니다. " + createMemberSnsDto.getMemberId()));

        //URL 유효성 검사
        if(memberSnsRepository.existsByUrl(createMemberSnsDto.getUrl()))
            throw new IllegalArgumentException("이미 등록되어 있는 URL 입니다. " + createMemberSnsDto.getUrl());

        //SNS 유효성 검사
        Sns sns = snsRepository.findById(createMemberSnsDto.getSnsId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 SNS 이름 입니다. " + createMemberSnsDto.getUrl()));

        //SNS URL 검사
        urlUtil.inspectionUrl(createMemberSnsDto.getUrl());

        //memberSNS 생성
        MemberSns memberSns = MemberSns.createMemberSns(member, sns, createMemberSnsDto.getUrl());

        //DB에 저장
        memberSnsRepository.save(memberSns);


    }

    /** 회원 SNS 삭제 */
    @Override
    public void deleteMemberSns(Long memberSnsId) {

        MemberSns memberSns = memberSnsRepository.findById(memberSnsId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 SNS 입니다. "));

        memberSnsRepository.delete(memberSns);
    }

}

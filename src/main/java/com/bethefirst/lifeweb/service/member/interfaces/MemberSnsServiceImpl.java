package com.bethefirst.lifeweb.service.member.interfaces;

import com.bethefirst.lifeweb.dto.member.request.CreateMemberSnsDto;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.member.MemberSns;
import com.bethefirst.lifeweb.entity.member.Sns;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import com.bethefirst.lifeweb.repository.member.MemberSnsRepository;
import com.bethefirst.lifeweb.repository.member.SnsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberSnsServiceImpl implements MemberSnsService{

    private final MemberRepository memberRepository;
    private final MemberSnsRepository memberSnsRepository;
    private final SnsRepository snsRepository;

    /** 회원 SNS 등록 **/
    @Override
    public void createMemberSns(CreateMemberSnsDto createMemberSnsDto, Long memberId) {
        //회원 유효성 검사
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 회원입니다. " + memberId));

        //URL 유효성 검사
        if(memberSnsRepository.existsBySnsUrl(createMemberSnsDto.getSnsUrl()))
            throw new IllegalArgumentException("이미 등록되어 있는 URL 입니다. " + createMemberSnsDto.getSnsUrl());

        //SNS 유효성 검사
        Sns sns = snsRepository.findById(createMemberSnsDto.getSnsId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 SNS 이름 입니다. " + createMemberSnsDto.getSnsUrl()));

        //SNS URL 검사
        inspectionUrl(createMemberSnsDto.getSnsUrl());

        //memberSNS 생성
        MemberSns memberSns = MemberSns.createMemberSns(member, sns, createMemberSnsDto.getSnsUrl());

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

    /** SNS URL 검사 */
    public void inspectionUrl(String snsUrl) {

        try {
            URL url = new URL(snsUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();

            if(String.valueOf(responseCode).charAt(0) == 4){
                throw new IllegalArgumentException("존재하지 않는 URL 입니다. " + snsUrl);
            }
        }catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL 형식이 잘못 되었습니다. " + snsUrl);
        } catch (IOException e) {
            throw new RuntimeException("URL 검사에 실패 하였습니다.");
        }

    }
}

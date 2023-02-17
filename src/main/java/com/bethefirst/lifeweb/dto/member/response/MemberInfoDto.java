package com.bethefirst.lifeweb.dto.member.response;

import com.bethefirst.lifeweb.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MemberInfoDto {

    private Long memberId; //회원 PK
    private String fileName;//프로필이미지
    private String name; //이름
    private String nickname;//닉네임
    private String gender;//성별
    private LocalDate birth;//생년월일
    private String tel;//전화번호
    private String postcode;//우편번호
    private String address;//주소
    private String detailAddress;//상세주소
    private String extraAddress;//참고사항
    private int point;//포인트
    private List<MemberSnsDto> memberSnsDtoList = new ArrayList<>();

    public MemberInfoDto(Member member) {
        this.memberId = member.getId();
        this.fileName = member.getFileName() == null ? null : member.getFileName();
        this.name = member.getName() == null ? null : member.getName();
        this.nickname = member.getNickname();
        this.gender = member.getGender() == null ? null : member.getGender();
        this.birth = member.getBirth() == null ? null : member.getBirth();
        this.tel = member.getTel() == null ? null : member.getTel();
        this.postcode = member.getPostcode() == null ? null : member.getPostcode();
        this.address = member.getAddress() == null ? null : member.getAddress();
        this.detailAddress = member.getDetailAddress() == null ? null : member.getDetailAddress();
        this.extraAddress = member.getExtraAddress() == null ? null : member.getExtraAddress();
        this.point = member.getPoint();
        this.memberSnsDtoList = member.getMemberSnsList().stream().map(MemberSnsDto::new).collect(Collectors.toList());
    }
}

package com.bethefirst.lifeweb.dto.member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberInfoDto {

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
}

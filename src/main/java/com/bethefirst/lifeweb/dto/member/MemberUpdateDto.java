package com.bethefirst.lifeweb.dto.member;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Slf4j
public class MemberUpdateDto {

    private String name;
    private String nickname;
    private String gender;
    private LocalDate birth;
    private String tel;
    private String postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;
    private MultipartFile fileName;
    private List<Long> snsId;
    private List<String> snsName;
    private List<String> snsUrl;


    /**
     * @return memberId, snsName, snsUrl 리스트를 사용하여 회원의 SNS 리스트를 만들어 반환 합니다.
     *      snsName이 null인 경우 빈 컬렉션을 반환합니다.
     */
    public List<MemberSnsDto> getMemberSnsDtoList(){

        if(snsName != null) {
            List<MemberSnsDto> memberSnsDtoList = new ArrayList<>();
            for (int i = 0; i < snsName.size(); i++) {
                    memberSnsDtoList.add(new MemberSnsDto(snsId.get(i), snsName.get(i), snsUrl.get(i)));
                }
                return memberSnsDtoList;
            }
        return Collections.emptyList();
    }

}

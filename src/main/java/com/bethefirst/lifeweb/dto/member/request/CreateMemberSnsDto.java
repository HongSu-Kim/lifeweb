package com.bethefirst.lifeweb.dto.member.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMemberSnsDto {

    private Long snsId; //SNS PK
    private String snsUrl;//SNS주소

}

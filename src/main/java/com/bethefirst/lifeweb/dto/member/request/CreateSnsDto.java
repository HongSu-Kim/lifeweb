package com.bethefirst.lifeweb.dto.member.request;

import com.bethefirst.lifeweb.entity.member.Sns;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSnsDto {
    private String name; //SNS 이름


    public Sns createSns(){
        return new Sns(name);
    }

}


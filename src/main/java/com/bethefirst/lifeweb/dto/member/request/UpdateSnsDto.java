package com.bethefirst.lifeweb.dto.member.request;

import com.bethefirst.lifeweb.entity.member.Sns;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSnsDto {

    private String name; //SNS이름

    public void updateSns(Sns sns){

        sns.updateSns(name);
    }
}

package com.bethefirst.lifeweb.dto.member.request;

import com.bethefirst.lifeweb.entity.member.Sns;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnsCreateDto {
    private String snsName;


    public Sns createSns(){
        return new Sns(snsName);
    }

}


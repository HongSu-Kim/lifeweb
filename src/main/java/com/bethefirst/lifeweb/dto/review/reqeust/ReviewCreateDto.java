package com.bethefirst.lifeweb.dto.review.reqeust;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateDto {

    private Long campaignId;
    private String reviewUrl;

}

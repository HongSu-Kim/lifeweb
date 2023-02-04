package com.bethefirst.lifeweb.dto.review.reqeust;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewSearchRequirements { //리뷰 검색조건

    private Long memberId;
    private Long campaignId;
    private List<Long> snsId;

}

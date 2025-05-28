package com.kh.ecolog.challenge.model.vo;

import java.util.Date;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Challenge {
    Long challengeSeq;
    String challengeTitle;
    String challengeContent;
    Date   enrollDate;
    String challengeStatus;
    Long   userId;
    String challengeImg;
    String userName;
}

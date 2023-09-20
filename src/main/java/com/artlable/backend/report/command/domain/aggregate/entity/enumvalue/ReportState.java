package com.artlable.backend.report.command.domain.aggregate.entity.enumvalue;

import lombok.Getter;

@Getter
public  enum ReportState {
    PENDING("IN_PENDING","접수"),
    REVIEW("IN_REVIEW","검토중"),
    RESOLVED("IN_RESOLVED","처리됨"),
    REJECTED("IN_REJECTED","거절됨");

    private final String key;
    private final String title;

    ReportState(String key, String title){
        this.key = key;
        this.title = title;
    }

}

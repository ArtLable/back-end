package com.artlable.backend.report.command.domain.aggregate.entity.enumvalue;

import lombok.Getter;

@Getter
public enum ReportTypeEnum {
    SPAM("IN_SPAM","스팸"),
    HARASSMENT("IN_HARASSMENT","괴롭힘 모욕"),
    FAKE_NEWS("IN_FAKE","거짓 정보"),
    INAPPROPRIATE_CONTENT("IN_INAPPROPRIATE","부적절 한 내용"),
    COPYRIGHT_INFRINGEMENT("IN_COPYRIGHT_INFRINGEMENT","저작권 침해"),
    OTHER("IN_OTHER","기타");

    private final String key;
    private final String title;

    ReportTypeEnum(String key, String title){
        this.key = key;
        this.title = title;
    }
}

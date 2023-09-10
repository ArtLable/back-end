package com.artlable.backend.member.command.domain.aggregate.entity.enumvalue;

import lombok.Getter;

@Getter
public enum MemberAgeRange {

    TEN("RANGE_TEN", "10대"),
    TWENTY("RANGE_TWENTY", "20대"),
    THIRSTY("RANGE_THIRSTY", "30대"),
    FORTY("RANGE_FORTY", "40대"),
    FIFTY("RANGE_FIFTY", "50대"),
    SIXTY("RANGE_SIXTY", "60대");
    private final String key;
    private final String title;

    MemberAgeRange(String key, String title) {
        this.title = title;
        this.key = key;
    }
}

package com.artlable.backend.member.command.domain.aggregate.entity.enumvalue;

import lombok.Getter;

@Getter
public  enum Role {
    GUEST("ROLE_GUEST","손님"),
    MEMBER("ROLE_MEMBER","사용자"),
    MEMBER2("ROLE_MEMBER2","작가"),
    ADMIN("ROLE_ADMIN","관리자");

    private final String key;
    private final String title;

    Role(String key, String title){
        this.key = key;
        this.title = title;
    }
}

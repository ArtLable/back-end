package com.artlable.backend.feed.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FeedSearchFilter {

    private String keyword;
    private String hashtag;
}

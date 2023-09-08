package com.artlable.backend.common;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseMessage {

    private int httpStatus;

    private String message;

    private Map<String, Object> results;


}

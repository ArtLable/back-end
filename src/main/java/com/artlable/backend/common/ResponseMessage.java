package com.artlable.backend.common;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMessage {

    private int httpStatus;
    private String message;
    private Map<String, Object> results;

    public ResponseMessage(int httpStatus, String message, Map<String, Object> results){
        this.httpStatus = httpStatus;
        this.message = message;
        this.results = results;
    }

}

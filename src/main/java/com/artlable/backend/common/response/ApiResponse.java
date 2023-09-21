package com.artlable.backend.common.response;

import com.artlable.backend.common.response.enumtype.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ApiResponse {

    private ApiStatus status;
    private String message;
    private Object data;

    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(ApiStatus.SUCCESS, message, data);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(ApiStatus.ERROR, message, null); //
    }
}

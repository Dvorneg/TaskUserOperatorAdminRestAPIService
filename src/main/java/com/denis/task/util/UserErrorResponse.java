package com.denis.task.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserErrorResponse{

    private String message;
    private long timestamp;

    public UserErrorResponse(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}

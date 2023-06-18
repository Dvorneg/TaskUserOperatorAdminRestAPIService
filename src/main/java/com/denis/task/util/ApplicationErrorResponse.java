package com.denis.task.util;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationErrorResponse{

    private String message;
    private long timestamp;

    public ApplicationErrorResponse(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

/*    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }*/
}

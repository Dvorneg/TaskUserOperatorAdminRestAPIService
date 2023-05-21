package com.denis.task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApplicationDTO {
    private String message;

/*    @Override
    public String toString() {
        //return "ApplicationDTO{" +
        return "{" +
                "\"message\":\"" + message + '"' +
                '}';
    }*/

/*    @Enumerated(EnumType.STRING)
    private Status status;*/

    public ApplicationDTO(String message) {
        this.message = message;
    }

}

package com.denis.task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ApplicationDTO {
    private String message;

/*    @Enumerated(EnumType.STRING)
    private Status status;*/


    public ApplicationDTO(String message) {
        this.message = message;
    }

}

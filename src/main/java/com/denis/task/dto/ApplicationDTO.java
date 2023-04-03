package com.denis.task.dto;

import com.denis.task.model.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
public class ApplicationDTO {

/*    @Enumerated(EnumType.STRING)
    private Status status;*/

    private String message;

}

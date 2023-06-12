package com.denis.task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ApplicationResponse {

    private List<ApplicationDTO> applications;

    public ApplicationResponse(List<ApplicationDTO> applicationDTO) {
        this.applications = applicationDTO;
    }

}

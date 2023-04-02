package com.denis.task.controllers;

import com.denis.task.model.Application;
import com.denis.task.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/app")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping()
    public List<Application> getApplications(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "app_per_page", required = false) Integer appPerPage,
            @RequestParam(value = "sortByASC", required = false) boolean sortBy)
    {
        return  applicationService.findWithPagination(page, appPerPage,sortBy);
    }


}

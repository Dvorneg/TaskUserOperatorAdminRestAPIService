package com.denis.task.controllers;

import com.denis.task.dto.ApplicationDTO;
import com.denis.task.model.Application;
import com.denis.task.model.User;
import com.denis.task.security.UserDetailsImpl;
import com.denis.task.service.ApplicationService;
import com.denis.task.util.ApplicationtException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.denis.task.util.ErrorsUtil.returnErrorsToClient;

@RestController
@Slf4j
@RequestMapping("/api/app")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ModelMapper modelMapper;

    @Autowired
    public ApplicationController(ApplicationService applicationService, ModelMapper modelMapper) {
        this.applicationService = applicationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<Application> getApplications(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "app_per_page", required = false) Integer appPerPage,
            @RequestParam(value = "sortByASC", required = false) boolean sortBy)
    {
        return  applicationService.findWithPagination(page, appPerPage,sortBy);
    }


    @PostMapping("/add")
    //public ResponseEntity<HttpStatus> add(@AuthenticationPrincipal User user ,@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid ApplicationDTO applicationDTO, BindingResult bindingResult) {

        Application applicationtToAdd = convertToApplication(applicationDTO);
        //Validator.validate(measurementToAdd,bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        applicationService.createApplication(applicationtToAdd);
        return ResponseEntity.ok(HttpStatus.OK);  //<> ok status 200
    }

    @PutMapping(value = "/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody ApplicationDTO applicationDTO, @PathVariable int id) {

        Application applicationtToAdd = convertToApplication(applicationDTO);
        applicationtToAdd.setId(id);
        int userId = userDetails.getUser().id();
        log.info("update {} for user {}", applicationDTO, userId);

/*        if (applicationtToAdd.isNew()) {
            applicationtToAdd.setId(id);
        } else if (applicationtToAdd.id() != id) {
            throw new ApplicationtException(bean.getClass().getSimpleName() + " must has id=" + id);
        }*/
        //repository.checkBelong(id, userId);
        applicationService.updateApplication(applicationtToAdd, userId);
        return ResponseEntity.ok(HttpStatus.OK);  //<> ok status 200
    }

    private Application convertToApplication(ApplicationDTO applicationDTO) {
        return modelMapper.map(applicationDTO, Application.class);
    }

}

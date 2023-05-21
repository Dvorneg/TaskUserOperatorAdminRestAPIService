package com.denis.task.controllers;

import com.denis.task.dto.ApplicationDTO;
import com.denis.task.model.Application;
import com.denis.task.security.UserDetailsImpl;
import com.denis.task.service.ApplicationService;
import com.denis.task.util.ApplicationErrorResponse;
import com.denis.task.util.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    @ResponseStatus(HttpStatus.CREATED)
    //public ResponseEntity<HttpStatus> add(@AuthenticationPrincipal User user ,@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid ApplicationDTO applicationDTO, BindingResult bindingResult) {

        Application applicationtToAdd = convertToApplication(applicationDTO);
        //Validator.validate(measurementToAdd,bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        applicationService.createApplication(applicationtToAdd);
        //return ResponseEntity.isOk(HttpStatus.OK);  //<> ok status 200
        return new ResponseEntity<>(HttpStatus.CREATED);  //<> CREATED status 201
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApplicationDTO> get(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int id) {
        int userId = userDetails.getUser().id();

        Application application = applicationService.getApplication(id, userId);
        return ResponseEntity.ok(convertToApplicationDTO(application));  //<> ok status 200

/*        Optional<Application> optionalApplication = applicationService.getApplication(id, userId);
        if (optionalApplication.isPresent()) {
            return ResponseEntity.of( (optionalApplication.map(this::convertToApplicationDTO)));
        }
        else
            throw new ApplicationException("Don't found app with id="+ id);*/
        //applicationService.updateApplication(applicationtToAdd, userId);
        //return ResponseEntity.ok(HttpStatus.OK);  //<> ok status 200

    }

    @GetMapping("/send/{id}")
    public ResponseEntity<HttpStatus> send(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int id) {
        int userId = userDetails.getUser().id();
        applicationService.send(id, userId);
        return ResponseEntity.ok(HttpStatus.OK);  //<> ok status 200
    }

    private Application convertToApplication(ApplicationDTO applicationDTO) {
        return modelMapper.map(applicationDTO, Application.class);
    }

    private ApplicationDTO convertToApplicationDTO(Application application){
        return modelMapper.map(application, ApplicationDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<ApplicationErrorResponse> handleException(ApplicationException e){

        ApplicationErrorResponse response = new ApplicationErrorResponse(
                e.getMessage()
                //System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );  //400, BAD_REQUEST
    }


}

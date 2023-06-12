package com.denis.task.controllers;

import com.denis.task.dto.ApplicationDTO;
import com.denis.task.dto.ApplicationResponse;
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
import java.util.stream.Collectors;

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApplicationDTO> get(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int id) {
        int userId = userDetails.getUser().id();

        Application application = applicationService.getApplication(id, userId);
        return ResponseEntity.ok(convertToApplicationDTO(application));  //<> ok status 200
        //     throw new ApplicationException("Don't found app with id="+ id);*/
    }

    @GetMapping()
    public ApplicationResponse getApplications(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "sortASC", required = false) boolean sortASC)
    {
        int userId = userDetails.getUser().id();
        Integer appPerPage = 5;
        if (page==null)
            page=0;
        log.info("page {} for user id{}, sortASC {}", page, userDetails.getUser().id(), sortASC);
        return new ApplicationResponse( applicationService.findWithPagination(userId, page, appPerPage, sortASC).stream().map(this::convertToApplicationDTO)
                .collect(Collectors.toList()));
    }

    @PutMapping(value = "/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @Valid @RequestBody ApplicationDTO applicationDTO, @PathVariable int id) {

        Application applicationtToAdd = convertToApplication(applicationDTO);
        applicationtToAdd.setId(id);
        int userId = userDetails.getUser().id();
        log.info("update {} for user id{}", applicationDTO, userId);
        // throw new ApplicationtException(bean.getClass().getSimpleName() + " must has id=" + id);
        applicationService.updateApplication(applicationtToAdd, userId);
        return ResponseEntity.ok(HttpStatus.OK);  //<> ok status 200
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid ApplicationDTO applicationDTO, BindingResult bindingResult) {

        Application applicationtToAdd = convertToApplication(applicationDTO);
        //Validator.validate(measurementToAdd,bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        applicationService.createApplication(applicationtToAdd);
        //return ResponseEntity.isOk(HttpStatus.OK);  //<> ok status 200
        return new ResponseEntity<>(HttpStatus.CREATED);  //<> CREATED status 201
    }

    @GetMapping("/send/{id}")
    public ResponseEntity<HttpStatus> send(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int id) {
        int userId = userDetails.getUser().id();
        applicationService.send(id, userId);
        return ResponseEntity.ok(HttpStatus.OK);  //<> ok status 200
    }

    @GetMapping("/nameSearch/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse searchByUserName(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "sortASC", required = false) boolean sortASC,
            @PathVariable String name){

        int userId = userDetails.getUser().id();
        Integer appPerPage = 5;
        if (page==null)
            page=0;
        log.info("nameSearch {}  operator for page {} for user id{}, sortASC {}", name, page, userDetails.getUser().id(), sortASC);
        return new ApplicationResponse( applicationService.findNameForOperatorWithPagination(userId, page, appPerPage, sortASC, name).stream().map(this::convertToApplicationDTO)
                .collect(Collectors.toList()));
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
                //log.info(System.currentTimeMillis())
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );  //400, BAD_REQUEST
    }


}

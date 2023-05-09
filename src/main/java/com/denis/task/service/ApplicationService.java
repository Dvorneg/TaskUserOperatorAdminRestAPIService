package com.denis.task.service;

import com.denis.task.model.Application;
import com.denis.task.model.Status;
import com.denis.task.repository.ApplicationRepository;
import com.denis.task.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Transactional
    public void createApplication(Application application) {
        enrichApplication(application);
        applicationRepository.save(application);
    }

    @Transactional
    public void updateApplication(Application application, Integer userId) {
        enrichApplication(application);
        //todo validate user
        applicationRepository.save(application);
    }


    @Transactional
    public void send(Integer applicationId, Integer userId) {
        Application application=applicationRepository.getById(applicationId);
        application.setStatus(Status.SEND);
        //todo validate user
        applicationRepository.save(application);
    }

    public Optional<Application> getApplication(Integer applicationId, Integer userId) {
        //enrichApplication(application);
        //todo validate user
        return applicationRepository.findById(applicationId);
    }

    public List<Application> findWithPagination(Integer page, Integer appPerPage, boolean sortByASC){
        if (sortByASC)
            return applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.ASC,"applicationDateTime"))).getContent();
        else
            return applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.DESC,"applicationDateTime"))).getContent();
    }

    private void enrichApplication(Application application) {
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        application.setUser(userDetails.getUser());
        application.setApplicationDateTime(LocalDateTime.now());
        application.setStatus(Status.DRAFT);
    }


}

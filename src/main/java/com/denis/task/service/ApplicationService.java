package com.denis.task.service;

import com.denis.task.model.Application;
import com.denis.task.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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
        applicationRepository.save(application);
    }

    public List<Application> findWithPagination(Integer page, Integer appPerPage, boolean sortByASC){
        if (sortByASC)
            return applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.ASC,"applicationDateTime"))).getContent();
        else
            return applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.DESC,"applicationDateTime"))).getContent();
    }

}

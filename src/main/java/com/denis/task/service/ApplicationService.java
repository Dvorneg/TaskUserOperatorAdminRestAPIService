package com.denis.task.service;

import com.denis.task.model.Application;
import com.denis.task.model.Role;
import com.denis.task.model.Status;
import com.denis.task.model.User;
import com.denis.task.repository.ApplicationRepository;
import com.denis.task.repository.UserRepository;
import com.denis.task.security.UserDetailsImpl;
import com.denis.task.util.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createApplication(Application application) {
        enrichApplication(application);
        applicationRepository.save(application);
    }

    @Transactional
    public void updateApplication(Application application, Integer userId) {
        enrichApplication(application);
        User user = userRepository.getById(userId);
        //Application application = applicationRepository.getById(applicationId);

        if (user.getRoles().contains(Role.USER) && application.getUser().id() == userId && application.getStatus()== Status.DRAFT) {
            applicationRepository.save(application);
        } else {
            throw new ApplicationException("Только пользователь может изменять свою заявку из статуса черновик!");
        }

        //applicationRepository.save(application);
    }


    @Transactional
    public void send(Integer applicationId, Integer userId) {

        User user = userRepository.getById(userId);
        Application application = applicationRepository.getById(applicationId);

        if (user.getRoles().contains(Role.USER) && application.getUser().id() == userId && application.getStatus()== Status.DRAFT) {
            application.setStatus(Status.SEND);
            applicationRepository.save(application);
        } else {
            throw new ApplicationException("Только пользователь может отправить свою заявку из статуса черновик!");
        }

    }

    public Application getApplication(Integer applicationId, Integer userId) {
        //enrichApplication(application);
        //todo validate user
        User user = userRepository.getById(userId);
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isPresent())
        {
            //user ok
            if (optionalApplication.get().getUser().id()==userId || user.getRoles().contains(Role.OPERATOR))
                return optionalApplication.get();
            else  {
                 throw new ApplicationException("Нет доступа к выбранной заявке!");
            }
        }
        throw new ApplicationException("Не найдена заявка по номеру:"+applicationId);
        //return null;
    }

    public List<Application> findWithPagination(Integer page, Integer appPerPage, boolean sortByASC){
        List<Application> applicationList = new ArrayList<>();
        if (sortByASC)
            applicationList=applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.ASC,"applicationDateTime"))).getContent();
            //return applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.ASC,"applicationDateTime"))).getContent();
        else
            applicationList= applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.DESC,"applicationDateTime"))).getContent();

        return applicationList;
        //todo replace dto?
    }

    private void enrichApplication(Application application) {
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        application.setUser(userDetails.getUser());
        application.setApplicationDateTime(LocalDateTime.now());
        application.setStatus(Status.DRAFT);
    }


}

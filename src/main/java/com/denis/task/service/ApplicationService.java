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
import java.util.stream.Collectors;

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
    }

    //task:
    // Оператор НЕ может просматривать заявки в статусе отличном от «отправлено»
    //Оператор может:
    // Просматривать все отправленные на рассмотрение  заявки с возможностью сортировки
    // по дате создания в оба направления (как от самой старой к самой новой, так и наоборот) и пагинацией по 5 элементов.
    //!!Просматривать отправленные заявки только конкретного пользователя по его имени/части имени (у пользователя, соотетственно, должно быть
    //поле name) с возможностью сортировки по дате создания в оба направления (как от самой старой к самой новой, так и наоборот) и пагинацией по 5 элементов.

    //Администратор НЕ может вообще видеть заявки

    //Пользователь может
	//создавать заявки
	//просматривать созданные им заявки с возможностью сортировки по дате создания в оба направления (как от самой старой к самой новой, так и наоборот) и пагинацией по 5 элементов
	//+редактировать созданные им заявки в статусе «черновик»
    public List<Application> findNameForOperatorWithPagination(Integer userId, Integer page, Integer appPerPage, boolean sortByASC, String UserNameSubstring) {
        User user = userRepository.getById(userId);
        List<Application> applicationList = new ArrayList<>();

        if (user.getRoles().contains(Role.OPERATOR)) {
            if (sortByASC)
                applicationList = applicationRepository.findAllByUserNameIsContainingIgnoreCase(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.ASC, "applicationDateTime")),
                        UserNameSubstring).getContent().stream().filter(Application::isSendStatus).collect(Collectors.toList());
                //return applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.ASC,"applicationDateTime"))).getContent();
            else
                applicationList = applicationRepository.findAllByUserNameIsContainingIgnoreCase(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.DESC, "applicationDateTime")),
                        UserNameSubstring).getContent().stream().filter(Application::isSendStatus).collect(Collectors.toList());

            return applicationList;
        }
        else throw new ApplicationException("У вас нет прав искать по имени пользователя!");
    }


    public List<Application> findWithPagination(Integer userId, Integer page, Integer appPerPage, boolean sortByASC){
        User user = userRepository.getById(userId);
        List<Application> applicationList = new ArrayList<>();


        if (user.getRoles().contains(Role.OPERATOR))
        {
            if (sortByASC)
                applicationList=applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.ASC,"applicationDateTime"))
                        ).getContent().stream().filter(Application::isSendStatus).collect(Collectors.toList());
                //return applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.ASC,"applicationDateTime"))).getContent();
            else
                applicationList=applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.DESC,"applicationDateTime"))
                        ).getContent().stream().filter(Application::isSendStatus).collect(Collectors.toList());

            return applicationList;
        }
        else if (user.getRoles().contains(Role.USER))

        //List<Application> applicationList = new ArrayList<>();
        if (sortByASC)
            applicationList=applicationRepository.findAllByUser(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.ASC,"applicationDateTime")),user).getContent();
            //return applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.ASC,"applicationDateTime"))).getContent();
        else
            applicationList= applicationRepository.findAllByUser(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.DESC,"applicationDateTime")),user).getContent();

        return applicationList;

    }

    private void enrichApplication(Application application) {
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        application.setUser(userDetails.getUser());
        application.setApplicationDateTime(LocalDateTime.now());
        application.setStatus(Status.DRAFT);
    }


}

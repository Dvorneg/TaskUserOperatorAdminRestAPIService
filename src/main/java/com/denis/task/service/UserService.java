package com.denis.task.service;

import com.denis.task.model.Role;
import com.denis.task.model.User;
import com.denis.task.repository.UserRepository;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //TODO а на до ли эту хрень?*
    public User get(Integer userId){
        return userRepository.getById(userId);
    }

    public List<User> getAll(Integer userId) {
        //toDo проверка что пользователь админ
        User user = userRepository.getById(userId);
        if (user.getRoles().contains(Role.ADMIN))
            return userRepository.findAll();
        else
            return Collections.emptyList();
    }

    public void addOperatorRole(int userId) {
        return;
    }
}
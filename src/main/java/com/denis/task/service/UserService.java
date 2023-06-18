package com.denis.task.service;

import com.denis.task.model.Role;
import com.denis.task.model.User;
import com.denis.task.repository.UserRepository;
import com.denis.task.util.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //use?
    public User get(Integer userId){
        return userRepository.getById(userId);
    }

    @Transactional
    public List<User> getAll(Integer userId) {
        User authUser = userRepository.getById(userId);
        if (authUser.getRoles().contains(Role.ADMIN))
            return userRepository.findAll();
        else
            throw new UserException("Недостаточно прав!:");
    }

    @Transactional
    public void addOperatorRole(int userId, int authUserId) {
        User authUser = userRepository.getById(authUserId);

        if(authUser.getRoles().contains(Role.ADMIN)){
            User user = userRepository.getById(userId);
            Set<Role> roles = user.getRoles();
            roles.add(Role.OPERATOR);
            //user.setRoles(roles);..
            userRepository.save(user);
        }
        else
            throw new UserException("Недостаточно прав!:");
    }

    public List<User> findUserByName(String userName, int userId) {
        User authUser = userRepository.getById(userId);

        if (authUser.getRoles().contains(Role.ADMIN)) {
            List<User> userList = userRepository.findAllByNameContainingIgnoreCase(userName);
            if (!userList.isEmpty()) {
                return userList;
            } else
                throw new UserException("Не найдены пользователи содержащие в имени текст:" + userName);
        }
        else
        {
            throw new UserException("Недостаточно прав!:");
        }
    }

}

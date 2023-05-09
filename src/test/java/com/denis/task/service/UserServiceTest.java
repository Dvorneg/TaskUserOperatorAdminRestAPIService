package com.denis.task.service;

import com.denis.task.controllers.UserTestData;
import com.denis.task.model.Role;
import com.denis.task.model.User;
import com.denis.task.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void GetAllUsersIfUserIsAdmin(){
        List<User> users = UserTestData.getUsers();
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userRepository.getById(UserTestData.ADMIN_ID)).thenReturn(UserTestData.admin);
        List<User> result= userService.getAll(UserTestData.ADMIN_ID);
        Assertions.assertNotNull(result, "Bad news, don`t works getAll User service!");
        Assertions.assertEquals(2 , result.size());
        Assertions.assertEquals(users.get(1) , result.get(1));
    }

    @Test
    public void GetAllUsersIfUserIsUser(){
        //List<User> users = getUsers();  don't get all users.
        Mockito.when(userRepository.getById(UserTestData.USER_ID)).thenReturn(UserTestData.user);
        List<User> result= userService.getAll(UserTestData.USER_ID);
        Assertions.assertNotNull(result, "Bad news, don`t works getAll User service!");
        Assertions.assertEquals(0 , result.size());
    }

    @Test
    public void GetAllUsersIfUserIsOperator(){
        Mockito.when(userRepository.getById(UserTestData.OPERATOR_ID)).thenReturn(UserTestData.operator);
        List<User> result= userService.getAll(UserTestData.OPERATOR_ID);
        Assertions.assertNotNull(result, "Bad news, don`t works getAll User service!");
        Assertions.assertEquals(0 , result.size());
    }

    //У пользователя системы может быть одновременно несколько ролей, например, «Оператор» и «Администратор».
    @Test
    public void UserWithMultipleRoles(){
        Mockito.when(userRepository.getById(UserTestData.MULTIPLE_ROLES_ID)).thenReturn(UserTestData.userMutipleRoles);
        User user = userService.get(UserTestData.MULTIPLE_ROLES_ID);

        Assertions.assertTrue(user.getRoles().contains(Role.OPERATOR) , "User not operator!");
        Assertions.assertTrue(user.getRoles().contains(Role.ADMIN), "User not administrator!");
    }

    @Test
    public void True(){
    }

}
package com.denis.task.controllers;

import com.denis.task.model.Role;
import com.denis.task.model.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class UserTestData {

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int OPERATOR_ID = 3;
    public static final int MULTIPLE_ROLES_ID = 4;

    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", true,new Date(), Collections.singleton(Role.USER));
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", true, new Date(), Collections.singleton(Role.ADMIN));
    public static final User operator = new User(ADMIN_ID, "Admin", "opator@mail.ru" , "123", true, new Date(), Collections.singleton(Role.OPERATOR));
    public static final User userMutipleRoles = new User(MULTIPLE_ROLES_ID, "MULTIPLE_ROLES_ID", "multiple@mail.ru" , "123", true, new Date(), Set.of(Role.OPERATOR, Role.ADMIN));

    public static List<User> getUsers(){
        //(Integer id, String name, String email, String password, boolean enabled, Date registered, Collection< Role > roles) {
        // User user1 = new User( 1, "tesuser","test1@mail.ru","test1@mail.ru",true, new Date(), Collections.singleton(Role.USER));
        User user = new User( 2, "tesuser2","test2@mail.ru","test2@mail.ru",true, new Date(), Collections.singleton(Role.ADMIN));
        return List.of(UserTestData.admin,UserTestData.user);
    }

}

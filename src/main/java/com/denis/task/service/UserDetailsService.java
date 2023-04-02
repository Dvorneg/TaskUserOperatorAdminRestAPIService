package com.denis.task.service;

import com.denis.task.model.User;
import com.denis.task.repository.UserRepository;
import com.denis.task.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> person= userRepository.findByName(s);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found /Den");

        return new UserDetailsImpl(person.get());

    }

}

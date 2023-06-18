package com.denis.task.repository;

import com.denis.task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //for UserDetailsService
    Optional<User> findByName(String name);

    List<User> findAllByNameContainingIgnoreCase(String name);
}

package com.denis.task.repository;

import com.denis.task.model.Application;
import com.denis.task.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    Page<Application> findAllByUser(Pageable pageable,User user);

    Page<Application>  findAllByUserNameIsContainingIgnoreCase(Pageable pageable, String name);

    //@Query("SELECT a.message FROM Application a WHERE p.title like %:title%")
/*    @Query("SELECT a.message FROM Application a")
    Page<Application> findAll(Pageable pageable);*/
}

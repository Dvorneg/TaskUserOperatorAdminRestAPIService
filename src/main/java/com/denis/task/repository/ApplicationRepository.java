package com.denis.task.repository;

import com.denis.task.model.Application;
import com.denis.task.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    //applicationRepository.findAll(PageRequest.of(page, appPerPage, Sort.by(Sort.Direction.DESC,"applicationDateTime"))).getContent();

    Page<Application> findAllByUser(Pageable pageable,User user);
    //Page<Application> findAllByNameContainingIgnoreCase(Pageable pageable, String name);

    //Page<Application> findAllByUserIsContainingIgnoreCase(Pageable pageable, String name);
    Page<Application>  findAllByUserNameIsContainingIgnoreCase(Pageable pageable, String name);

    //@Query("SELECT a.message FROM Application a WHERE p.title like %:title%")
/*    @Query("SELECT a.message FROM Application a")
    Page<Application> findAll(Pageable pageable);*/


}

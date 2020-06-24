package com.myservice.auth.repository;

import java.util.Optional;

import com.myservice.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u from User u inner join  u.company c where  LOWER(u.username) = LOWER(:username) and LOWER(c.code) =  LOWER(:code) and u.activated = true")
    Optional<User> findByCodeCompanyAndUsername(@Param("code") String code, @Param("username") String username);

}
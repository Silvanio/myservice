package com.myservice.auth.repository;

import com.myservice.auth.model.User;
import com.myservice.common.domain.StatusEnum;
import com.myservice.common.dto.auth.UserDTO;
import com.myservice.common.repository.IRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends IRepository<Long, User, UserDTO>{

    @Query("SELECT u from User u inner join  u.company c where  LOWER(u.username) = LOWER(:username) and LOWER(c.code) =  LOWER(:code) and u.status = :status")
    Optional<User> findByCodeCompanyAndUsernameAndStatus(@Param("code") String code, @Param("username") String username, @Param("status") StatusEnum status);

    @Query("SELECT u from User u inner join  u.company c where  LOWER(u.username) = LOWER(:username) and LOWER(c.code) =  LOWER(:code)")
    Optional<User> findByCodeCompanyAndUsername(@Param("code") String code, @Param("username") String username);
}
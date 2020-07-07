package com.myservice.auth.repository;

import com.myservice.auth.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

    Authority findByName(String name);

    @Query("SELECT aut from Authority aut inner join  aut.appModule app where  aut.client = :isClient and app.id = :idAppModule")
    List<Authority> findByClientAndModuleId(@Param("isClient") boolean isClient,@Param("idAppModule")Long idAppModule);

    @Query("SELECT aut from Authority aut inner join  aut.appModule app where app.id = :idAppModule")
    List<Authority> findByModuleId(@Param("idAppModule")Long idAppModule);
}
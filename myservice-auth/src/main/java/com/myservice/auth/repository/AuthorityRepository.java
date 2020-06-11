package com.myservice.auth.repository;

import com.myservice.auth.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

    Authority findByName(String name);

}
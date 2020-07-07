package com.myservice.auth.repository;

import com.myservice.auth.model.Contract;
import com.myservice.common.dto.auth.ContractDTO;
import com.myservice.common.repository.IRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContractRepository extends IRepository<Long, Contract, ContractDTO> {

    @Query("SELECT ct from Contract ct inner join  ct.company c where c.id = :idCompany")
    List<Contract> findByCompanyId(@Param("idCompany") Long idCompany);
}
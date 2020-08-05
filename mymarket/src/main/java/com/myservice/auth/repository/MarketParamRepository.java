package com.myservice.auth.repository;

import com.myservice.auth.model.MarketParam;
import com.myservice.auth.model.enumerator.CodeParamEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MarketParamRepository extends JpaRepository<MarketParam, Long>, JpaSpecificationExecutor<MarketParam> {
    MarketParam findByCode(CodeParamEnum code);
}
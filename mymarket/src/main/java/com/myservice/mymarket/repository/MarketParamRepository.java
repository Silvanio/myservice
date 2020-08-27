package com.myservice.mymarket.repository;

import com.myservice.mymarket.model.MarketParam;
import com.myservice.mymarket.model.enumerator.CodeParamEnum;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MarketParamRepository extends JpaRepository<MarketParam, Long>, JpaSpecificationExecutor<MarketParam> {

    @Cacheable("MyMarketParamCache")
    MarketParam findByCode(CodeParamEnum code);
}
package com.myservice.auth.repository;

import com.myservice.auth.model.StockHistoricalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StockHistoricalDataRepository extends JpaRepository<StockHistoricalData, Long>, JpaSpecificationExecutor<StockHistoricalData> {
}
package com.myservice.mymarket.repository;

import com.myservice.mymarket.model.StockHistoricalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StockHistoricalDataRepository extends JpaRepository<StockHistoricalData, Long>, JpaSpecificationExecutor<StockHistoricalData> {

    @Query(value = "SELECT max(dateQuote) FROM StockHistoricalData hist where hist.codeStock = :code")
    Date getMaxDateQuoteByCodeStock(@Param("code")String code);

    List<StockHistoricalData> findFirst10ByCodeStockOrderByDateQuoteDesc(String codeStock);

    List<StockHistoricalData> findFirst20ByCodeStockOrderByDateQuoteDesc(String codeStock);
}
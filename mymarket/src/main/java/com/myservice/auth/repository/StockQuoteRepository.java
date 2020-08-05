package com.myservice.auth.repository;

import com.myservice.auth.model.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StockQuoteRepository extends JpaRepository<StockQuote, Long>, JpaSpecificationExecutor<StockQuote> {
}
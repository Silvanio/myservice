package com.myservice.mymarket.repository;

import com.myservice.mymarket.model.StockQuote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockQuoteRepository extends JpaRepository<StockQuote, Long>, JpaSpecificationExecutor<StockQuote> {

    StockQuote findByCodeStock(String code);

    @Query("SELECT stkq from StockQuote stkq, Stock stk inner join stk.product prd where stkq.priceVariationDay > 0 and stkq.codeStock = stk.code and prd.id = :idProduct")
    Page<StockQuote> highLightsStockUP(@Param("idProduct") Long idProduct, Pageable pageable);


    @Query("SELECT stkq from StockQuote stkq, Stock stk inner join stk.product prd where stkq.priceVariationDay < 0 and stkq.codeStock = stk.code and prd.id = :idProduct")
    Page<StockQuote> highLightsStockDOWN(@Param("idProduct") Long idProduct, Pageable pageable);
}
package com.myservice.mymarket.repository;

import com.myservice.mymarket.model.Product;
import com.myservice.mymarket.model.Stock;
import com.myservice.mymarket.model.dto.StockDTO;
import com.myservice.common.repository.IRepository;

import java.util.List;

public interface StockRepository extends IRepository<Long, Stock, StockDTO> {
    List<Stock> findByCodeIgnoreCaseContaining(String stock);
    Stock findByCode(String stock);
    List<Stock> findByProduct(Product product);
    List<Stock> findByProductAndRefreshQuote(Product product, boolean refreshStock);
    List<Stock> findTop20ByCodeStartsWithIgnoreCase(String codeStock);
}
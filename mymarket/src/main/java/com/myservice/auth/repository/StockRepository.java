package com.myservice.auth.repository;

import com.myservice.auth.model.Stock;
import com.myservice.auth.model.dto.StockDTO;
import com.myservice.common.repository.IRepository;

import java.util.List;

public interface StockRepository extends IRepository<Long, Stock, StockDTO> {
    List<Stock> findByCodeIgnoreCaseContaining(String stock);
    Stock findByCode(String stock);

}
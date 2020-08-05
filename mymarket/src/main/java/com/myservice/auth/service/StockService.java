package com.myservice.auth.service;

import com.myservice.auth.model.Stock;
import com.myservice.auth.model.dto.StockDTO;
import com.myservice.common.service.IService;

public interface StockService extends IService<Long, Stock, StockDTO> {

}

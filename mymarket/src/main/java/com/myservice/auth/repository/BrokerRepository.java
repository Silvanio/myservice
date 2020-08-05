package com.myservice.auth.repository;

import com.myservice.auth.model.Broker;
import com.myservice.auth.model.Product;
import com.myservice.auth.model.dto.BrokerDTO;
import com.myservice.auth.model.dto.ProductDTO;
import com.myservice.common.repository.IRepository;

public interface BrokerRepository extends IRepository<Long, Broker, BrokerDTO> {


}
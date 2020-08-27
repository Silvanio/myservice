package com.myservice.mymarket.service.impl;

import com.myservice.mymarket.model.Broker;
import com.myservice.mymarket.model.dto.BrokerDTO;
import com.myservice.mymarket.repository.BrokerRepository;
import com.myservice.mymarket.service.BrokerService;
import com.myservice.common.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BrokerServiceImpl extends MyService<Long, Broker, BrokerDTO> implements BrokerService {

    @Autowired
    BrokerRepository brokerRepository;

    @Override
    public BrokerRepository getRepository() {
        return brokerRepository;
    }
}

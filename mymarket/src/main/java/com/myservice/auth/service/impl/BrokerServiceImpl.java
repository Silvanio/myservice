package com.myservice.auth.service.impl;

import com.myservice.auth.model.Broker;
import com.myservice.auth.model.dto.BrokerDTO;
import com.myservice.auth.repository.BrokerRepository;
import com.myservice.auth.service.BrokerService;
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

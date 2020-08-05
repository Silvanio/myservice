package com.myservice.auth.controller;

import com.myservice.auth.model.Broker;
import com.myservice.auth.model.Sector;
import com.myservice.auth.model.dto.BrokerDTO;
import com.myservice.auth.model.dto.SectorDTO;
import com.myservice.auth.service.BrokerService;
import com.myservice.auth.service.SectorService;
import com.myservice.common.controller.MyController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/broker")
@Api(value = "Sector API")
public class BrokerController extends MyController<Long, Broker, BrokerDTO> {

    @Autowired
    BrokerService brokerService;


    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("MY_MARKET_ADMIN");
    }

    @Override
    public BrokerService getService() {
        return brokerService;
    }

}
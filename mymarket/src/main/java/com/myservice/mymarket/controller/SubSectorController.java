package com.myservice.mymarket.controller;

import com.myservice.mymarket.model.SubSector;
import com.myservice.mymarket.model.dto.SubSectorDTO;
import com.myservice.mymarket.service.SubSectorService;
import com.myservice.common.controller.MyController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sub-sector")
@Api(value = "Stock API")
public class SubSectorController extends MyController<Long, SubSector, SubSectorDTO> {

    @Autowired
    SubSectorService subSectorService;

    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("MY_MARKET_ADMIN");
    }

    @Override
    public SubSectorService getService() {
        return subSectorService;
    }

}
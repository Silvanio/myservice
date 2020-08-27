package com.myservice.mymarket.controller;

import com.myservice.mymarket.model.Sector;
import com.myservice.mymarket.model.dto.SectorDTO;
import com.myservice.mymarket.service.SectorService;
import com.myservice.common.controller.MyController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sector")
@Api(value = "Sector API")
public class SectorController extends MyController<Long, Sector, SectorDTO> {

    @Autowired
    SectorService sectorService;


    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("MY_MARKET_ADMIN");
    }

    @Override
    public SectorService getService() {
        return sectorService;
    }

}
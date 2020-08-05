package com.myservice.auth.controller;

import com.myservice.auth.model.Sector;
import com.myservice.auth.model.Stock;
import com.myservice.auth.model.dto.SectorDTO;
import com.myservice.auth.model.dto.StockDTO;
import com.myservice.auth.service.SectorService;
import com.myservice.auth.service.StockService;
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
package com.myservice.auth.controller;

import com.myservice.auth.model.Segment;
import com.myservice.auth.model.Stock;
import com.myservice.auth.model.dto.SegmentDTO;
import com.myservice.auth.model.dto.StockDTO;
import com.myservice.auth.service.SegmentService;
import com.myservice.auth.service.StockService;
import com.myservice.common.controller.MyController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/segment")
@Api(value = "Stock API")
public class SegmentController extends MyController<Long, Segment, SegmentDTO> {

    @Autowired
    SegmentService segmentService;

    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("MY_MARKET_ADMIN");
    }

    @Override
    public SegmentService getService() {
        return segmentService;
    }

}
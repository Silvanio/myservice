package com.myservice.mymarket.controller;

import com.myservice.mymarket.model.Segment;
import com.myservice.mymarket.model.dto.SegmentDTO;
import com.myservice.mymarket.service.SegmentService;
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
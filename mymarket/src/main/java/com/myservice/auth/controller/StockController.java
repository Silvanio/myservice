package com.myservice.auth.controller;

import com.myservice.auth.model.Stock;
import com.myservice.auth.model.dto.StockDTO;
import com.myservice.auth.service.StockService;
import com.myservice.common.controller.MyController;
import com.myservice.common.dto.common.ResponseMessageDTO;
import com.myservice.common.dto.pagination.OrderDTO;
import com.myservice.common.dto.pagination.PageDTO;
import com.myservice.common.dto.pagination.PageableDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stock")
@Api(value = "Stock API")
public class StockController extends MyController<Long, Stock, StockDTO> {

    @Autowired
    StockService stockService;

    @ApiOperation(value = "List All Pageable", response = PageDTO.class)
    @PostMapping("/findAll")
    public PageDTO<StockDTO> findAll(@RequestBody PageableDTO<Stock, StockDTO> pageableDTO) {

        if (pageableDTO.getOrderDTO() == null) {
            pageableDTO.setOrderDTO(new ArrayList<>());
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setField("product.name");
            orderDTO.setDirection(Sort.Direction.ASC);
            pageableDTO.getOrderDTO().add(orderDTO);
        }
        return pageToDTO(getService().findAll(pageableDTO));
    }

    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("MY_MARKET_ADMIN");
    }

    @Override
    public StockService getService() {
        return stockService;
    }

}
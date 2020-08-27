package com.myservice.mymarket.controller;

import com.myservice.common.dto.common.ResponseMessageDTO;
import com.myservice.common.dto.pagination.OrderDTO;
import com.myservice.common.dto.pagination.PageDTO;
import com.myservice.common.dto.pagination.PageableDTO;
import com.myservice.mymarket.model.StockQuote;
import com.myservice.mymarket.model.TrackMarketUser;
import com.myservice.mymarket.model.dto.ProductDTO;
import com.myservice.mymarket.model.dto.StockDTO;
import com.myservice.mymarket.model.dto.StockQuoteDTO;
import com.myservice.mymarket.model.dto.TrackMarketUserDTO;
import com.myservice.mymarket.repository.shared.AuthClient;
import com.myservice.mymarket.service.TrackMarketUserService;
import com.myservice.common.controller.MyController;
import com.myservice.common.dto.auth.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/track-market")
@Api(value = "Acompanhar Mercado API")
public class TrackMarketUserController extends MyController<Long, TrackMarketUser, TrackMarketUserDTO> {

    @Autowired
    TrackMarketUserService trackMarketService;

    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("MY_MARKET_ADMIN");
        authorities.add("MY_MARKET_USER");
    }

    @ApiOperation(value = "Maiores Altas da Bolsa", response = List.class)
    @PostMapping("/highLightsStockUP")
    public PageDTO<StockQuoteDTO> highLightsStockUP(@RequestBody PageableDTO<StockQuote, StockQuoteDTO> pageableDTO) {
        pageableDTO.setOrderDTO(new ArrayList<>());
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setField("priceVariationDay");
        orderDTO.setDirection(Sort.Direction.DESC);
        pageableDTO.getOrderDTO().add(orderDTO);
        return pageToDTOStockQuote(this.getService().highLightsStockUP(pageableDTO));
    }

    @ApiOperation(value = "Maiores Baixas da Bolsa", response = List.class)
    @PostMapping("/highLightsStockDOWN")
    public PageDTO<StockQuoteDTO> highLightsStockDOWN(@RequestBody PageableDTO<StockQuote, StockQuoteDTO> pageableDTO) {
        pageableDTO.setOrderDTO(new ArrayList<>());
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setField("priceVariationDay");
        orderDTO.setDirection(Sort.Direction.ASC);
        pageableDTO.getOrderDTO().add(orderDTO);
        return pageToDTOStockQuote(this.getService().highLightsStockDOWN(pageableDTO));
    }

    @ApiOperation(value = "Save list MarketUser", response = List.class)
    @PostMapping("/saveList")
    public Collection<StockQuoteDTO> saveList(@RequestBody List<StockDTO> stockDTOList) {
        this.getService().saveList(stockDTOList);
        return this.listMyMarketQuotes();
    }

    @ApiOperation(value = "Remove My Stock", response = List.class)
    @PostMapping("/removeMyCardStock")
    public ResponseMessageDTO removeMyCardStock(@RequestBody String codeStock) {
        this.getService().removeMyCardStock(codeStock);
        return ResponseMessageDTO.get("msg_general_success");
    }

    @ApiOperation(value = "List Stock Quotes (BTC,IFIX and IBOV)", response = List.class)
    @GetMapping("/listBaseMarketQuotes")
    public Collection<StockQuoteDTO> listBaseMarketQuotes() {
        return this.getService().listBaseMarketQuotes();
    }

    @ApiOperation(value = "List My Stock Quotes", response = List.class)
    @PostMapping("/listMyMarketQuotes")
    public Collection<StockQuoteDTO> listMyMarketQuotes() {
        return this.getService().listMyMarketQuotes();
    }


    protected PageDTO pageToDTOStockQuote(final Page<StockQuote> page) {
        PageDTO<StockQuoteDTO> pageDTO = new PageDTO<StockQuoteDTO>();
        try {
            final ModelMapper modelMapper = new ModelMapper();
            final Collection<StockQuoteDTO> collect = page.getContent()
                    .stream()
                    .map(e -> modelMapper.map(e, StockQuoteDTO.class))
                    .collect(Collectors.toList());
            pageDTO.setContent(collect);
            pageDTO.setTotalElements(page.getTotalElements());
        } catch (Exception e) {
            pageDTO.setTotalElements(0);
        }
        return pageDTO;
    }

    @Override
    public TrackMarketUserService getService() {
        return trackMarketService;
    }

}
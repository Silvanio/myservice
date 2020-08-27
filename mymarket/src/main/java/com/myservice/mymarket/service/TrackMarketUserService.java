package com.myservice.mymarket.service;

import com.myservice.common.dto.pagination.PageableDTO;
import com.myservice.mymarket.model.StockQuote;
import com.myservice.mymarket.model.TrackMarketUser;
import com.myservice.mymarket.model.dto.ProductDTO;
import com.myservice.mymarket.model.dto.StockDTO;
import com.myservice.mymarket.model.dto.StockQuoteDTO;
import com.myservice.mymarket.model.dto.TrackMarketUserDTO;
import com.myservice.common.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Collection;
import java.util.List;

public interface TrackMarketUserService extends IService<Long, TrackMarketUser, TrackMarketUserDTO> {

    Collection<StockQuoteDTO> listBaseMarketQuotes();
    Collection<StockQuoteDTO> listMyMarketQuotes();
    void saveList(List<StockDTO> stockDTOList);
    void removeMyCardStock(String codeStock);
    Page<StockQuote> highLightsStockUP(PageableDTO<StockQuote, StockQuoteDTO> pageableDTO);
    Page<StockQuote> highLightsStockDOWN(PageableDTO<StockQuote, StockQuoteDTO> pageableDTO);
}

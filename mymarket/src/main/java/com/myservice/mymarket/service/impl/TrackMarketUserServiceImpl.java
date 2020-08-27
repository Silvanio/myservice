package com.myservice.mymarket.service.impl;

import com.myservice.common.dto.auth.UserDTO;
import com.myservice.common.dto.pagination.PageableDTO;
import com.myservice.common.exceptions.BusinessException;
import com.myservice.common.exceptions.MessageException;
import com.myservice.common.service.MyService;
import com.myservice.mymarket.model.Stock;
import com.myservice.mymarket.model.StockHistoricalData;
import com.myservice.mymarket.model.StockQuote;
import com.myservice.mymarket.model.TrackMarketUser;
import com.myservice.mymarket.model.dto.StockDTO;
import com.myservice.mymarket.model.dto.StockHistoricalDataDTO;
import com.myservice.mymarket.model.dto.StockQuoteDTO;
import com.myservice.mymarket.model.dto.TrackMarketUserDTO;
import com.myservice.mymarket.repository.StockHistoricalDataRepository;
import com.myservice.mymarket.repository.StockQuoteRepository;
import com.myservice.mymarket.repository.StockRepository;
import com.myservice.mymarket.repository.TrackMarketUserRepository;
import com.myservice.mymarket.repository.shared.AuthClient;
import com.myservice.mymarket.service.TrackMarketUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrackMarketUserServiceImpl extends MyService<Long, TrackMarketUser, TrackMarketUserDTO> implements TrackMarketUserService {

    public static final String IBOV = "IBOV";
    public static final String IFIX = "IFIX";
    public static final String BTC = "BTC";
    public static final String IDIV = "IDIV";

    private static final Logger logger = LoggerFactory.getLogger(TrackMarketUserServiceImpl.class);


    @Autowired
    TrackMarketUserRepository trackMarketUserRepository;

    @Autowired
    StockQuoteRepository stockQuoteRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockHistoricalDataRepository stockHistoricalDataRepository;

    @Autowired
    AuthClient authUserClient;


    @Override
    public Page<StockQuote> highLightsStockUP(PageableDTO<StockQuote, StockQuoteDTO> pageableDTO) {
        return stockQuoteRepository.highLightsStockUP(pageableDTO.getDto().getIdProduct(), pageableDTO);
    }

    @Override
    public Page<StockQuote> highLightsStockDOWN(PageableDTO<StockQuote, StockQuoteDTO> pageableDTO) {
        return stockQuoteRepository.highLightsStockDOWN(pageableDTO.getDto().getIdProduct(), pageableDTO);
    }

    @Override
    public void saveList(List<StockDTO> stockDTOList) {
        if (stockDTOList == null || stockDTOList.isEmpty()) {
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), "msg_empty_stock");
        }
        List<Stock> stocks = new ArrayList<>();
        for (StockDTO stockDTO : stockDTOList) {
            Stock stock = stockRepository.findById(stockDTO.getId()).get();
            stocks.add(stock);
        }
        saveStockList(stocks);
    }

    @Override
    public void removeMyCardStock(String codeStock) {
        if (codeStock == null || codeStock.isEmpty()) {
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), "msg_empty_stock");
        }
        UserDTO userDTO = authUserClient.getCurrentUserInfo();
        Stock stock = stockRepository.findByCode(codeStock);
        List<TrackMarketUser> trackMarketUserList = trackMarketUserRepository.findByXidUserAndStock(userDTO.getId(), stock);
        if (trackMarketUserList != null && !trackMarketUserList.isEmpty()) {
            trackMarketUserRepository.deleteAll(trackMarketUserList);
        }
    }

    @Override
    public Collection<StockQuoteDTO> listBaseMarketQuotes() {
        List<Stock> stocks = getListStockBase();
        return getStockQuoteByStocks(stocks);
    }

    @Override
    public Collection<StockQuoteDTO> listMyMarketQuotes() {
        UserDTO userDTO = authUserClient.getCurrentUserInfo();
        List<TrackMarketUser> trackMarketUsers = trackMarketUserRepository.findByXidUser(userDTO.getId());
        if (trackMarketUsers == null || trackMarketUsers.isEmpty()) {
            List<Stock> stocksBase = getListStockBase();
            saveStockList(stocksBase);
            trackMarketUsers = trackMarketUserRepository.findByXidUser(userDTO.getId());
        }
        List<Stock> stocks = trackMarketUsers.stream().map(TrackMarketUser::getStock).collect(Collectors.toList());
        return getStockQuoteByStocks(stocks);
    }


    @Override
    public void delete(Long idEntity) {
        Optional<TrackMarketUser> optionalEntity = getRepository().findById(idEntity);
        if (optionalEntity.isPresent()) {
            TrackMarketUser entity = optionalEntity.get();
            getRepository().delete(entity);
        } else {
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), MessageException.MSG_GENERAL_ERROR.getMessage());
        }
    }

    private Collection<StockQuoteDTO> getStockQuoteByStocks(List<Stock> stocks) {
        final ModelMapper modelMapper = new ModelMapper();

        List<StockQuoteDTO> stockQuoteDTOList = new ArrayList<>();
        for (Stock stock : stocks) {
            StockQuote quote = stockQuoteRepository.findByCodeStock(stock.getCode());
            if (quote != null) {
                StockQuoteDTO stockQuoteDTO = modelMapper.map(quote, StockQuoteDTO.class);
                stockQuoteDTO.setStock(modelMapper.map(stock, StockDTO.class));
                List<StockHistoricalData> StockHistoricalDataList = stockHistoricalDataRepository.findFirst20ByCodeStockOrderByDateQuoteDesc(stock.getCode());
                if (StockHistoricalDataList != null && !StockHistoricalDataList.isEmpty()) {

                    List<StockHistoricalDataDTO> stockHistoricalDataList = StockHistoricalDataList
                            .stream()
                            .map(e -> modelMapper.map(e, StockHistoricalDataDTO.class))
                            .collect(Collectors.toList());

                    if (stockHistoricalDataList != null && !stockHistoricalDataList.isEmpty()) {

                        Date maxDate = stockHistoricalDataList.stream().map(u -> u.getDateQuote()).max(Date::compareTo).get();

                        List<StockHistoricalDataDTO> newHistoricalList = new ArrayList<>();
                        if (maxDate.before(quote.getDateQuote())) {
                            StockHistoricalDataDTO now = new StockHistoricalDataDTO();
                            now.setClose(quote.getPrice());
                            now.setCodeStock(quote.getCodeStock());
                            now.setExchange(quote.getExchange());
                            now.setDateQuote(quote.getDateQuote());
                            now.setHigh(quote.getHigh());
                            now.setLow(quote.getLow());
                            now.setOpen(quote.getOpen());
                            now.setVolume(quote.getVolume());
                            newHistoricalList.add(now);
                        }

                        newHistoricalList.addAll(stockHistoricalDataList);
                        stockQuoteDTO.setStockHistoricalDataList(newHistoricalList);
                    }
                }
                stockQuoteDTOList.add(stockQuoteDTO);
            }
        }
        return stockQuoteDTOList;
    }

    private void saveStockList(List<Stock> stocks) {
        UserDTO userDTO = authUserClient.getCurrentUserInfo();
        for (Stock stock : stocks) {
            StockQuote stockQuote = stockQuoteRepository.findByCodeStock(stock.getCode());
            if (stockQuote == null) {
                throw new BusinessException("msg_stock_quote_unavailable", stock.getCode());
            }
            List<TrackMarketUser> trackBDList = trackMarketUserRepository.findByXidUserAndStock(userDTO.getId(), stock);
            if (trackBDList == null || trackBDList.isEmpty()) {
                TrackMarketUser trackMarketUser = new TrackMarketUser();
                trackMarketUser.setStock(stock);
                trackMarketUser.setXidUser(userDTO.getId());
                trackMarketUserRepository.save(trackMarketUser);
            }
        }
    }

    private List<Stock> getListStockBase() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stockRepository.findByCode(IBOV));
        stocks.add(stockRepository.findByCode(IFIX));
        stocks.add(stockRepository.findByCode(IDIV));
        stocks.add(stockRepository.findByCode(BTC));
        return stocks;
    }

    @Override
    public TrackMarketUserRepository getRepository() {
        return trackMarketUserRepository;
    }

}

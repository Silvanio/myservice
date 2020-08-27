package com.myservice.mymarket.repository;

import com.myservice.mymarket.model.Stock;
import com.myservice.mymarket.model.TrackMarketUser;
import com.myservice.mymarket.model.dto.TrackMarketUserDTO;
import com.myservice.common.repository.IRepository;

import java.util.List;

public interface TrackMarketUserRepository extends IRepository<Long, TrackMarketUser, TrackMarketUserDTO> {
    List<TrackMarketUser> findByXidUser(Long xidUser);
    List<TrackMarketUser> findByXidUserAndStock(Long id, Stock stock);
}
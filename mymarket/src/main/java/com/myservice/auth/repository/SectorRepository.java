package com.myservice.auth.repository;

import com.myservice.auth.model.Sector;
import com.myservice.auth.model.Stock;
import com.myservice.auth.model.dto.SectorDTO;
import com.myservice.auth.model.dto.StockDTO;
import com.myservice.common.repository.IRepository;

public interface SectorRepository extends IRepository<Long, Sector, SectorDTO> {

    Sector findByName(String sector);
}
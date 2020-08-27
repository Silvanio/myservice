package com.myservice.mymarket.repository;

import com.myservice.mymarket.model.Sector;
import com.myservice.mymarket.model.dto.SectorDTO;
import com.myservice.common.repository.IRepository;

public interface SectorRepository extends IRepository<Long, Sector, SectorDTO> {

    Sector findByName(String sector);
}
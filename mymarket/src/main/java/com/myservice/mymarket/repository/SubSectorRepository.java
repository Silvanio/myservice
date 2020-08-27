package com.myservice.mymarket.repository;

import com.myservice.mymarket.model.SubSector;
import com.myservice.mymarket.model.dto.SubSectorDTO;
import com.myservice.common.repository.IRepository;

public interface SubSectorRepository extends IRepository<Long, SubSector, SubSectorDTO> {
    SubSector findByName(String subSector);
}
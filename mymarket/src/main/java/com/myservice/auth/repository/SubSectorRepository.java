package com.myservice.auth.repository;

import com.myservice.auth.model.SubSector;
import com.myservice.auth.model.dto.SubSectorDTO;
import com.myservice.common.repository.IRepository;

public interface SubSectorRepository extends IRepository<Long, SubSector, SubSectorDTO> {
    SubSector findByName(String subSector);
}
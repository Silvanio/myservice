package com.myservice.auth.service.impl;

import com.myservice.auth.model.Sector;
import com.myservice.auth.model.dto.SectorDTO;
import com.myservice.auth.repository.SectorRepository;
import com.myservice.auth.service.SectorService;
import com.myservice.common.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SectorServiceImpl extends MyService<Long, Sector, SectorDTO> implements SectorService {

    @Autowired
    SectorRepository sectorRepository;

    @Override
    public SectorRepository getRepository() {
        return sectorRepository;
    }
}

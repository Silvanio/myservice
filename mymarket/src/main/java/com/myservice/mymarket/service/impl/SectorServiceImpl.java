package com.myservice.mymarket.service.impl;

import com.myservice.mymarket.model.Sector;
import com.myservice.mymarket.model.dto.SectorDTO;
import com.myservice.mymarket.repository.SectorRepository;
import com.myservice.mymarket.service.SectorService;
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

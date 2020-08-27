package com.myservice.mymarket.service.impl;

import com.myservice.mymarket.model.SubSector;
import com.myservice.mymarket.model.dto.SubSectorDTO;
import com.myservice.mymarket.repository.SubSectorRepository;
import com.myservice.mymarket.service.SubSectorService;
import com.myservice.common.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SubSectorServiceImpl extends MyService<Long, SubSector, SubSectorDTO> implements SubSectorService {

    @Autowired
    SubSectorRepository subSectorRepository;

    @Override
    public SubSectorRepository getRepository() {
        return subSectorRepository;
    }
}

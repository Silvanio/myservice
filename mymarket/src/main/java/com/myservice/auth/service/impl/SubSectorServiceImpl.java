package com.myservice.auth.service.impl;

import com.myservice.auth.model.SubSector;
import com.myservice.auth.model.dto.SubSectorDTO;
import com.myservice.auth.repository.SubSectorRepository;
import com.myservice.auth.service.SubSectorService;
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

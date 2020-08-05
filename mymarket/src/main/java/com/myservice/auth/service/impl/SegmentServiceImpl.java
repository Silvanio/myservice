package com.myservice.auth.service.impl;

import com.myservice.auth.model.Segment;
import com.myservice.auth.model.dto.SegmentDTO;
import com.myservice.auth.repository.SegmentRepository;
import com.myservice.auth.service.SegmentService;
import com.myservice.common.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SegmentServiceImpl extends MyService<Long, Segment, SegmentDTO> implements SegmentService {

    @Autowired
    SegmentRepository segmentRepository;

    @Override
    public SegmentRepository getRepository() {
        return segmentRepository;
    }
}

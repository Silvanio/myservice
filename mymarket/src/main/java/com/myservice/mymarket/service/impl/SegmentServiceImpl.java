package com.myservice.mymarket.service.impl;

import com.myservice.mymarket.model.Segment;
import com.myservice.mymarket.model.dto.SegmentDTO;
import com.myservice.mymarket.repository.SegmentRepository;
import com.myservice.mymarket.service.SegmentService;
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

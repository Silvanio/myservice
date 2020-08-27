package com.myservice.mymarket.repository;

import com.myservice.mymarket.model.Segment;
import com.myservice.mymarket.model.dto.SegmentDTO;
import com.myservice.common.repository.IRepository;

public interface SegmentRepository extends IRepository<Long, Segment, SegmentDTO> {
    Segment findByName(String segment);
}
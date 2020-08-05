package com.myservice.auth.repository;

import com.myservice.auth.model.Segment;
import com.myservice.auth.model.Stock;
import com.myservice.auth.model.dto.SegmentDTO;
import com.myservice.auth.model.dto.StockDTO;
import com.myservice.common.repository.IRepository;

public interface SegmentRepository extends IRepository<Long, Segment, SegmentDTO> {
    Segment findByName(String segment);
}
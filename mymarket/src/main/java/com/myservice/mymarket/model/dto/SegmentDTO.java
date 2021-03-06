package com.myservice.mymarket.model.dto;

import com.myservice.common.dto.common.MyDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SegmentDTO extends MyDTO {
    private String name;
    private SubSectorDTO subSector;
}

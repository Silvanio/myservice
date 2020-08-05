package com.myservice.auth.model.dto;

import com.myservice.common.dto.common.MyDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubSectorDTO extends MyDTO {
    private String name;
    private SectorDTO sector;
}

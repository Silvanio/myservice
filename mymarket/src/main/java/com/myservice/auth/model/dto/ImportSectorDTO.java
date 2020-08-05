package com.myservice.auth.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ImportSectorDTO {
    private String segment;
    private String subSector;
    private String sector;
    private List<String> stocks;
}

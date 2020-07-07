package com.myservice.common.dto.pagination;

import com.myservice.common.dto.common.IDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class PageDTO<D extends IDTO> {
    private Collection<D> content;
    private long totalElements;
}

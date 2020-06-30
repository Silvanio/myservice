package com.myservice.common.dto.pagination;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class OrderDTO {
    private String field;
    private Sort.Direction direction;
}

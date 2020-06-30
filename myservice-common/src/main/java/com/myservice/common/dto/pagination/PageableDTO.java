package com.myservice.common.dto.pagination;

import com.myservice.common.domain.IEntity;
import com.myservice.common.dto.IDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageableDTO<E extends IEntity, D extends IDTO> implements Pageable {

    private int pageSize;

    private int pageNumber;

    private int offset;

    private E entity;

    private D dto;

    private List<OrderDTO> orderDTO;

    private List<FilterDTO> filterDTO;

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        if (orderDTO == null || orderDTO.isEmpty()) {
            return Sort.unsorted();
        }
        return Sort.by(orderDTO.stream().map((order) -> new Sort.Order(order.getDirection(), order.getField())).collect(Collectors.toList()));
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}

package com.myservice.common.service;

import com.myservice.common.domain.IEntity;
import com.myservice.common.domain.StatusEnum;
import com.myservice.common.dto.common.IDTO;
import com.myservice.common.dto.pagination.PageableDTO;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface IService<ID extends Serializable, E extends IEntity, D extends IDTO> {

    Page<E> findAll(PageableDTO<E, D> pageableDTO);

    void save(E entity);

    void update(E entity);

    void delete(ID idEntity);

    E get(ID id);

    List<E> listAll();

    List<E> findAllByStatus(StatusEnum status);
}

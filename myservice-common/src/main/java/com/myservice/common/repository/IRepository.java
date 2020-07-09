package com.myservice.common.repository;

import com.myservice.common.domain.IEntity;
import com.myservice.common.domain.StatusEnum;
import com.myservice.common.dto.common.IDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

public interface IRepository<ID extends Serializable, E extends IEntity, D extends IDTO> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
    List<E> findAllByStatus(StatusEnum status);
}

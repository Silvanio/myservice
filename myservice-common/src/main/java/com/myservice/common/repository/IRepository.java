package com.myservice.common.repository;

import com.myservice.common.domain.IEntity;
import com.myservice.common.dto.IDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface IRepository<ID extends Serializable, E extends IEntity, D extends IDTO> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
}

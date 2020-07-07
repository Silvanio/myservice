package com.myservice.common.service;

import com.myservice.common.domain.IEntity;
import com.myservice.common.domain.MyEntity;
import com.myservice.common.domain.StatusEnum;
import com.myservice.common.dto.common.IDTO;
import com.myservice.common.dto.pagination.FilterDTO;
import com.myservice.common.dto.pagination.PageableDTO;
import com.myservice.common.exceptions.BusinessException;
import com.myservice.common.exceptions.MessageException;
import com.myservice.common.repository.IRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class MyService<ID extends Serializable, E extends IEntity, D extends IDTO> implements IService<ID, E, D> {


    @Override
    public Page<E> findAll(PageableDTO<E, D> pageableDTO) {
        final Example<E> filters = this.getFilterByExample(pageableDTO);
        if (filters == null) {
            return getRepository().findAll(pageableDTO);
        } else {
            return getRepository().findAll(filters, pageableDTO);
        }
    }

    @Override
    public void save(E entity) {
        prepareSave(entity);
        getRepository().save(entity);
    }

    @Override
    public void update(E entity) {
        prepareUpdate(entity);
        getRepository().save(entity);
    }

    @Override
    public void delete(ID idEntity) {
        Optional<E> optionalEntity = getRepository().findById(idEntity);
        if (optionalEntity.isPresent()) {
            E entity = optionalEntity.get();
            MyEntity myEntity = (MyEntity) entity;
            myEntity.setStatus(StatusEnum.INACTIVE);
            getRepository().save(entity);
        } else {
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), MessageException.MSG_GENERAL_ERROR.getMessage());
        }
    }

    @Override
    public E get(ID idEntity) {
        Optional<E> optionalEntity = getRepository().findById(idEntity);
        return optionalEntity.orElse(null);
    }

    @Override
    public List<E> listAll() {
        return getRepository().findAll();
    }

    protected Example<E> getFilterByExample(PageableDTO<E, D> pageableDTO) {

        if (pageableDTO == null || pageableDTO.getEntity() == null) {
            return null;
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        if(pageableDTO.getFilterDTO() != null && !pageableDTO.getFilterDTO().isEmpty()){
            for (FilterDTO filter : pageableDTO.getFilterDTO()) {
                matcher = matcher.withMatcher(filter.getField(), filter.getType().get());
            }
        }

        return Example.of(pageableDTO.getEntity(), matcher);
    }

    protected void validate(E entity) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<E>> validate = validator.validate(entity);
        if (validate.size() > 0) {
            ConstraintViolation<E> violation = validate.iterator().next();
            throw new BusinessException(MessageException.MSG_GENERAL_VALIDATE.getMessage(), violation.getMessage());
        }
    }

    protected void prepareUpdate(E entity) {
        validate(entity);
    }

    protected void prepareSave(E entity) {
        validate(entity);
    }

    public abstract IRepository<ID, E, D> getRepository();
}

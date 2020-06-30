package com.myservice.common.controller;

import com.myservice.common.domain.IEntity;
import com.myservice.common.dto.IDTO;
import com.myservice.common.dto.ResponseMessageDTO;
import com.myservice.common.dto.pagination.PageDTO;
import com.myservice.common.dto.pagination.PageableDTO;
import com.myservice.common.service.IService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MyController<ID extends Serializable, E extends IEntity, D extends IDTO> {

    @ApiOperation(value = "List All Pageable", response = PageDTO.class)
    @PostMapping("/findAll")
    public PageDTO<D> findAll(@RequestBody PageableDTO<E, D> pageableDTO) {
        return pageToDTO(getService().findAll(pageableDTO));
    }

    @ApiOperation(value = "Save entity", response = ResponseMessageDTO.class)
    @PostMapping("/save")
    public ResponseMessageDTO save(@RequestBody E entity) {
        this.getService().save(entity);
        return ResponseMessageDTO.get("msg_general_success");
    }

    @ApiOperation(value = "Update entity", response = ResponseMessageDTO.class)
    @PostMapping("/update")
    public ResponseMessageDTO update(@RequestBody E entity) {
        this.getService().update(entity);
        return ResponseMessageDTO.get("msg_general_success");
    }


    @ApiOperation(value = "Delete entity", response = ResponseMessageDTO.class)
    @PostMapping("/delete")
    public ResponseMessageDTO delete(@RequestBody ID idEntity) {
        this.getService().delete(idEntity);
        return ResponseMessageDTO.get("msg_general_success");
    }

    @PostMapping("/get")
    @ApiOperation(value = "Get entity", response = IEntity.class)
    public E dtoGet(@RequestBody ID idEntity) {
        return this.getService().get(idEntity);
    }

    @ApiOperation(value = "List All entity", response = List.class)
    @GetMapping("/list")
    public List<E> listAll() {
        return this.getService().listAll();
    }

    protected PageDTO pageToDTO(final Page<E> page) {
        PageDTO<D> pageDTO = new PageDTO<D>();

        try {
            Class<D> persistentClass = (Class<D>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[2];

            final ModelMapper modelMapper = new ModelMapper();

            final Collection<D> collect = page.getContent()
                    .stream()
                    .map(e -> modelMapper.map(e, persistentClass))
                    .collect(Collectors.toList());

            pageDTO.setContent(collect);
            pageDTO.setTotalElements(page.getTotalElements());
        } catch (Exception e) {
            pageDTO.setTotalElements(0);
        }
        return pageDTO;
    }

    public abstract IService<ID, E, D> getService();
}

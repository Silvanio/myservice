package com.myservice.common.controller;

import com.myservice.common.domain.Authorities;
import com.myservice.common.domain.IEntity;
import com.myservice.common.domain.StatusEnum;
import com.myservice.common.dto.auth.AppModuleDTO;
import com.myservice.common.dto.common.IDTO;
import com.myservice.common.dto.common.ResponseMessageDTO;
import com.myservice.common.dto.pagination.PageDTO;
import com.myservice.common.dto.pagination.PageableDTO;
import com.myservice.common.service.IService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe Generica para controladores.
 * <p>
 * Toda classe possui um Authorização global. caso for sempre permitir sobrescrever o metodo @isPermitAll.
 * <p>
 * Caso for definir as authorizações sobrescrever o método @permitAuthorities
 * <p>
 * Caso tiver authorizações diferentes no seu método basta criar o @PreAuthorize no seu metodo que ele irá sobrescrever o da classe.
 */
@PreAuthorize("hasAnyAuthority(#root.this.listAuthorities()) or #root.this.isPermitAll()")
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

    @ApiOperation(value = "Save entity", response = ResponseMessageDTO.class)
    @PostMapping("/saveDTO")
    public ResponseMessageDTO saveDTO(@RequestBody D entityDTO) {
        E entity = convertDTOtoEntity(entityDTO);
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
    @PreAuthorize("isAuthenticated()")
    public E get(@RequestBody ID idEntity) {
        return this.getService().get(idEntity);
    }

    @PostMapping("/getDTO")
    @ApiOperation(value = "Get entity", response = IDTO.class)
    @PreAuthorize("isAuthenticated()")
    public D getDTO(@RequestBody ID idEntity) {
        E entity = this.getService().get(idEntity);
        D dto = convertEntityToDTO(entity);
        return dto;
    }

    @ApiOperation(value = "List All entity", response = List.class)
    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public Collection<E> listAll() {
        return this.getService().listAll();
    }

    @ApiOperation(value = "List All entity", response = List.class)
    @GetMapping("/findAllActiveDTO")
    @PreAuthorize("isAuthenticated()")
    public Collection<D> findAllActiveDTO() {
        Collection<E> content = this.getService().findAllByStatus(StatusEnum.ACTIVE);
        return convertListEntityToDTO(content) ;
    }

    protected PageDTO pageToDTO(final Page<E> page) {
        PageDTO<D> pageDTO = new PageDTO<D>();
        try {
            final Collection<D> collect = convertListEntityToDTO(page.getContent());
            pageDTO.setContent(collect);
            pageDTO.setTotalElements(page.getTotalElements());
        } catch (Exception e) {
            pageDTO.setTotalElements(0);
        }
        return pageDTO;
    }

    protected Collection<D> convertListEntityToDTO(Collection<E> listEntity) {
        Class<D> persistentClass = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        final ModelMapper modelMapper = new ModelMapper();
        return listEntity
                .stream()
                .map(e -> modelMapper.map(e, persistentClass))
                .collect(Collectors.toList());
    }

    protected E convertDTOtoEntity(D entityDTO) {
        Class<E> persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        final ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entityDTO, persistentClass);
    }

    protected D convertEntityToDTO(E entity) {
        Class<D> persistentClass = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        final ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, persistentClass);
    }

    public abstract IService<ID, E, D> getService();

    public List<String> listAuthorities() {
        List<String> authorities = new ArrayList<>();
        permitAuthorities(authorities);
        return authorities;
    }

    /**
     * Sobrescrever para definir as authorizações globais da classe. caso tiver especificas de méthodo basta criar o @PreAuthorize no Método.
     *
     * @param authorities Authorizações
     */
    public void permitAuthorities(List<String> authorities) {
    }

    /**
     * Sobrescrever caso for definir a classe como PermitAll
     *
     * @return true para permitAll
     */
    public boolean isPermitAll() {
        return false;
    }
}

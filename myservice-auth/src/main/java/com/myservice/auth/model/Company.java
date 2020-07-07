package com.myservice.auth.model;

import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;


@Setter
@Getter
@Entity
@Table(name = "company", schema = "auth")
public class Company extends MyEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "msg_required_fields")
    private String code;

    @Column(nullable = false)
    @NotBlank(message = "msg_required_fields")
    private String name;

    @Column(name = "fiscal_number")
    @NotBlank(message = "msg_required_fields")
    private String fiscalNumber;

    private Boolean client;

    private String address;

    private String comments;

}

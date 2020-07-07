package com.myservice.auth.model;

import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "app_module", schema = "auth")
@Setter
@Getter
public class  AppModule extends MyEntity {

    @Column(nullable = false)
    @NotEmpty(message = "msg_required_fields")
    private String name;

    @Column(nullable = false)
    @NotEmpty(message = "msg_required_fields")
    private String description;

    @Column(name = "web_url")
    @NotEmpty(message = "msg_required_fields")
    private String webUrl;
}

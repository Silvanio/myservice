package com.myservice.auth.model;

import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "app_module", schema = "auth")
@Setter
@Getter
public class AppModule extends MyEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "web_url")
    private String webUrl;

}

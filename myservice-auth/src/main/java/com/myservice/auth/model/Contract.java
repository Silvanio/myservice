package com.myservice.auth.model;


import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "contract", schema = "auth")
public class Contract extends MyEntity {


    @Column(name = "count_user")
    private Integer countUser;

    @ManyToOne
    @JoinColumn(name="id_company",nullable = false)
    @NotNull(message = "msg_required_fields")
    private Company company;

    @Column(name = "dt_initial")
    @Temporal(value = TemporalType.TIMESTAMP)
    @NotNull(message = "msg_required_fields")
    private Date initialDate;

    @Column(name = "dt_final")
    @Temporal(value = TemporalType.TIMESTAMP)
    @NotNull(message = "msg_required_fields")
    private Date finalDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull(message = "msg_required_fields")
    @NotEmpty(message = "msg_required_fields")
    @JoinTable(
            name = "contract_module", schema = "auth",
            joinColumns = @JoinColumn(name = "id_contract"),
            inverseJoinColumns = @JoinColumn(name = "app_module"))
    private List<AppModule> appModules;

}

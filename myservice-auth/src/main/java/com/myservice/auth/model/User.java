package com.myservice.auth.model;

import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Table(name = "user", schema = "auth")
@Entity
@Setter
@Getter
public class User extends MyEntity {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "msg_required_fields")
    @Column(nullable = false)
    private String username;

    @NotNull(message = "msg_required_fields")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "msg_required_fields")
    @Size(max = 500)
    private String password;

    @Email(message = "msg_email_invalid")
    @Size(max = 100)
    @NotNull(message = "msg_required_fields")
    private String email;

    @ManyToOne
    @JoinColumn(name="id_company",nullable = false)
    @NotNull(message = "msg_required_fields")
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_authority", schema = "auth",
            joinColumns = @JoinColumn(name = "id_username"),
            inverseJoinColumns = @JoinColumn(name = "authority"))
    @NotNull(message = "msg_required_fields")
    @NotEmpty(message = "msg_required_fields")
    private Set<Authority> authorities;

}
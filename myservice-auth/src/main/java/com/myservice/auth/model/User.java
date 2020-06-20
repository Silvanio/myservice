package com.myservice.auth.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;


@Table(name = "user", schema = "auth")
@Entity
@Setter
@Getter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Size(max = 500)
    private String password;

    @Email
    @Size(max = 100)
    private String email;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean activated;

    @ManyToOne
    @JoinColumn(name="id_company",nullable = false)
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "user_authority", schema = "auth",
            joinColumns = @JoinColumn(name = "id_username"),
            inverseJoinColumns = @JoinColumn(name = "authority"))
    private Set<Authority> authorities;

}
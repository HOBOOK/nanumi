package com.daou.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="TEST_ACCOUNT")
public class TempAccount {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany
    @JoinColumn(name = "TEST_ACCOUNT_ID", referencedColumnName = "id")
    private List<UserRole> roles;

    public TempAccount(){}

    public TempAccount(Long id, String username, String password, List<UserRole> roles)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}

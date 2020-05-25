package com.daou.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
public class TempAccount {
    private Long id;

    private String username;

    private String password;

    public List<UserRole> getRoles(){
        return null;
    }

//    @OneToMany
//    @JoinColumn(name = "roles", referencedColumnName = "id")
//    private List<UserRole> roles;

    public TempAccount(Long id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
//        this.roles = roles;
    }

    public TempAccount() {}

}

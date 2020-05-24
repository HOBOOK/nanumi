package com.daou.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Data
public class TempAccount {
    private Long id;
    private String username;
    private String password;
    private List<UserRole> roles;

    @Builder
    public TempAccount(Long id, String username, String email, String password, List<UserRole> roles)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}

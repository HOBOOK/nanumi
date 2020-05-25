package com.daou.entity;

import lombok.Builder;
import java.util.List;

public class TempAccountDto {
    private Long id;
    private String username;
    private String password;
    private List<UserRole> roles;

    public TempAccount toEntity(){
        return TempAccount.builder()
                .id(id)
                .username(username)
                .password(password)
                .roles(roles)
                .build();
    }

    @Builder
    public TempAccountDto(Long id, String username, String password, List<UserRole> roles)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}

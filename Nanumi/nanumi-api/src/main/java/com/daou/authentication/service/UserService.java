package com.daou.authentication.service;

import com.daou.entity.TempAccount;

import java.util.Optional;

/**
 * @author pkh879
 */
public interface UserService {
    public Optional<TempAccount> getByUsername(String username);
}

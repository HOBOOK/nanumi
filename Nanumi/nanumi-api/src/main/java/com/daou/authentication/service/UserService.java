package com.daou.authentication.service;

import com.daou.entity.Account;

import java.util.Optional;

/**
 * @author pkh879
 */
public interface UserService {
    public Optional<Account> getByName(String username);
}

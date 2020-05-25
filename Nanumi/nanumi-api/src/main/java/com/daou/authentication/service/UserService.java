package com.daou.authentication.service;

import com.daou.entity.Member;

import java.util.Optional;

/**
 * @author pkh879
 */
public interface UserService {
    public Optional<Member> getByName(String username);
}

package com.daou.repository;

import com.daou.entity.TempAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 임시 Repository 클래스
 * @author pkh879
 */
public interface UserRepository extends CrudRepository<TempAccount, Long> {
    public Optional<TempAccount> findByUsername(@Param("username") String username);
}

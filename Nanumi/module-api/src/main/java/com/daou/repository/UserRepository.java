package com.daou.repository;

import com.daou.entity.TempAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 임시 Repository 클래스
 * @author pkh879
 */
public interface UserRepository extends JpaRepository<TempAccount, Long> {
    @Query("select u from TempAccount u left join fetch u.roles r where u.username=:username")
    public Optional<TempAccount> findByUsername(@Param("username") String username);
}

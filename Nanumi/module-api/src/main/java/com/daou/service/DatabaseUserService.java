package com.daou.service;

import com.daou.authentication.service.UserService;
import com.daou.entity.TempAccount;
import com.daou.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * 임시 테스트용 서비스 클래스
 * @author pkh879
 */
@Getter
@Service
public class DatabaseUserService implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public DatabaseUserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<TempAccount> getByUsername(String username){
        return this.userRepository.findByUsername(username);
    }
}

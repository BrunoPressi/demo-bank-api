package com.bank_api.services;

import com.bank_api.entities.User;
import com.bank_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public User newUser(User user) {
        return userRepository.save(user);
    }

}

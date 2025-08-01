package com.bank_api.services;

import com.bank_api.dto.UserCreateDTO;
import com.bank_api.dto.UserResponseDTO;
import com.bank_api.dto.mapper.UserMapper;
import com.bank_api.entities.User;
import com.bank_api.exceptions.DuplicateEmailException;
import com.bank_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public UserResponseDTO newUser(UserCreateDTO userCreateDTO) {
        User user = UserMapper.parseObject(userCreateDTO, User.class);
        try {
            userRepository.save(user);
        }
        catch(DataIntegrityViolationException e) {
            throw new DuplicateEmailException(String.format("Email |%s| already registered.", user.getEmail()));
        }
        return UserMapper.parseObject(user, UserResponseDTO.class);
    }

}

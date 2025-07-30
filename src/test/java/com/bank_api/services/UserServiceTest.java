package com.bank_api.services;

import com.bank_api.dto.UserCreateDTO;
import com.bank_api.dto.UserResponseDTO;
import com.bank_api.dto.mapper.UserMapper;
import com.bank_api.entities.User;
import com.bank_api.entities.enums.Role;
import com.bank_api.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void CreateNewUserSuccess() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test@gmail.com", "123456");
        User user = UserMapper.parseObject(userCreateDTO, User.class);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserResponseDTO result = userService.newUser(userCreateDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("test@gmail.com", result.getEmail());
    }

}

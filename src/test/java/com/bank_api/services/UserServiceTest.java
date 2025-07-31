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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private UserService userService;

    @Test
    void CreateNewUserSuccess() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test@gmail.com", "123456");
        User user = UserMapper.parseObject(userCreateDTO, User.class);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserResponseDTO result = userService.newUser(userCreateDTO);
        Mockito.verify(userRepository).save(user);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("test@gmail.com", result.getEmail());
    }

}

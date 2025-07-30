package com.bank_api.services;

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
    void newUserTest() {

        User user = new User(1L,"test@gmail.com", "123456", Role.ROLE_CUSTOMER);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User result = userService.newUser(user);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("test@gmail.com", result.getEmail());
        Assertions.assertEquals("123456", result.getPassword());
    }

}

package com.bank_api.api.controllers;

import com.bank_api.dto.UserCreateDTO;
import com.bank_api.dto.UserResponseDTO;
import com.bank_api.dto.validator.ValidationOrder;
import com.bank_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> newUser(@Validated(ValidationOrder.class) @RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO userResponseDTO = userService.newUser(userCreateDTO);

        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userResponseDTO.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(location)).body(userResponseDTO);
    }

}

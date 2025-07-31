package com.bank_api.web.controllers;

import com.bank_api.web.exception.ErrorMessage;
import com.bank_api.dto.UserCreateDTO;
import com.bank_api.dto.UserResponseDTO;
import com.bank_api.dto.validator.ValidationOrder;
import com.bank_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "User Controller", description = "Operations related to User entity")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create new user", description = "Operation to register a new user",
        responses = {
            @ApiResponse(responseCode = "201", description = "User create successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Email already exists",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Invalid input data",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })
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

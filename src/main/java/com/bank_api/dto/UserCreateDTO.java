package com.bank_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    @NotBlank(message = "Cannot be null or contain whitespace")
    @Email(message = "Invalid email")
    @Size(max = 50, min = 3, message = "Must be greater than 3 characters and less than 50 characters")
    private String email;

    @NotBlank(message = "Cannot be null or contain whitespace")
    @Size(min = 8, max = 12, message = "Must be greater than 8 characters and less than 12 characters")
    private String password;

}

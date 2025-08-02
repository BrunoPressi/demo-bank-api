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
public class EmailDTO {

    public interface Step1 {}  // @NotBlank
    public interface Step3 {}  // @Email
    public interface Step2 {}  // @Size

    @NotBlank(message = "Cannot be null or contain whitespace", groups = UserCreateDTO.Step1.class)
    @Email(regexp = ".+@.+\\..+", message = "Invalid email format", groups = UserCreateDTO.Step2.class)
    @Size(max = 50, min = 3, message = "Must be greater than 3 characters and less than 50 characters", groups = UserCreateDTO.Step3.class)
    private String email;

}

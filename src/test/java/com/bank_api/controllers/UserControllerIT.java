package com.bank_api.controllers;

import com.bank_api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(scripts = "/sql/UserSQL/UserInsert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/UserSQL/UserDelete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createNewUserReturn201() throws Exception {
        String userCreateDTO =
            """
            {
                "email": "bob@gmail.com",
                "password": "12345678"
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userCreateDTO))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/users/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("bob@gmail.com"));
    }

    @Test
    void createNewUserExistsEmailReturn409() throws Exception {
        String userCreateDTO =
                """
                {
                    "email": "john@gmail.com",
                    "password": "12345678"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.message").value("Email |john@gmail.com| already registered."))
                .andExpect(jsonPath("$.code").value(409))
                .andExpect(jsonPath("$.statusMessage").value("Conflict"));
    }

    @Test
    void createNewUserInvalidEmailReturn422() throws Exception {

        // Testing @Email annotation

        String userCreateDTO =
                """
                {
                    "email": "johngmail.com",
                    "password": "12345678"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.email").value("Invalid email format"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));

        userCreateDTO =
                """
                {
                    "email": "john@",
                    "password": "12345678"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.email").value("Invalid email format"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));

        userCreateDTO =
                """
                {
                    "email": "@gmail.com",
                    "password": "12345678"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.email").value("Invalid email format"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));

        userCreateDTO =
                """
                {
                    "email": "john@gmail.com    ",
                    "password": "12345678"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.email").value("Invalid email format"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));

        userCreateDTO =
                """
                {
                    "email": "john@@gmail.com",
                    "password": "12345678"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.email").value("Invalid email format"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));

        userCreateDTO =
                """
                {
                    "email": "john@email",
                    "password": "12345678"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.email").value("Invalid email format"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));

        // Testing @NotBlank annotation

        userCreateDTO =
                """
                {
                    "email": "    ",
                    "password": "12345678"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.email").value("Cannot be null or contain whitespace"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));

        // Testing @Size annotation

        userCreateDTO =
                """
                {
                    "email": "ab@gmail.com",
                    "password": "12345678"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.email").value("Must be greater than 3 characters and less than 50 characters"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));

    }

    @Test
    void createNewUserInvalidPasswordReturn422() throws Exception {

        String userCreateDTO =
                """
                {
                    "email": "bob@gmail.com",
                    "password": "12345"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.password").value("Must be greater than 8 characters and less than 12 characters"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));

        userCreateDTO =
                """
                {
                    "email": "bob@gmail.com",
                    "password": "123456789101112"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.password").value("Must be greater than 8 characters and less than 12 characters"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));

        userCreateDTO =
                """
                {
                    "email": "bob@gmail.com",
                    "password": ""
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.password").value("Cannot be null or contain whitespace"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));

        userCreateDTO =
                """
                {
                    "email": "bob@gmail.com",
                    "password": "         "
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateDTO))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.path").value("/api/v1/users"))
                .andExpect(jsonPath("$.errors.password").value("Cannot be null or contain whitespace"))
                .andExpect(jsonPath("$.code").value(422))
                .andExpect(jsonPath("$.statusMessage").value("Unprocessable Entity"));
    }

}

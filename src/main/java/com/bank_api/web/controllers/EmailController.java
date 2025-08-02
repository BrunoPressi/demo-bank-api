package com.bank_api.web.controllers;

import com.bank_api.dto.EmailDTO;
import com.bank_api.dto.validator.ValidationOrder;
import com.bank_api.entities.Token;
import com.bank_api.entities.User;
import com.bank_api.entities.enums.TokenType;
import com.bank_api.services.EmailService;
import com.bank_api.services.TokenService;
import com.bank_api.services.UserService;
import com.bank_api.services.UuidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final TokenService tokenService;
    private final UserService userService;
    private final UuidService uuidService;

    @PostMapping
    public ResponseEntity<String> sendEmail(@Validated(ValidationOrder.class) @RequestBody EmailDTO emailDTO) {

        String email = emailDTO.getEmail();
        UUID uuid = uuidService.uuidGenerator();
        User user = userService.findByEmail(email);
        String subject = "Email Verification Link";

        emailService.send(email, subject, uuid);
        tokenService.saveToken(uuid, user, TokenType.EMAIL_VALIDATION);

        return ResponseEntity.ok().body("Email sent successfully");
    }

    @PostMapping("/confirmation")
    public ResponseEntity<String> confirmEmail(@RequestParam(name = "uuid") UUID uuid, @RequestParam(name = "email") String email) {

        Token tokenDB = tokenService.findUuidByEmail(email);
        uuidService.validationUuid(tokenDB, uuid);
        userService.updateValidEmail(email);
        tokenService.deleteTokenByEmail(email);

        return ResponseEntity.ok().body("Email confirmed successfully");
    }

}

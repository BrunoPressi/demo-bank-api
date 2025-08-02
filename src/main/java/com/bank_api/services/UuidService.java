package com.bank_api.services;

import com.bank_api.entities.Token;
import com.bank_api.entities.enums.TokenType;
import com.bank_api.exceptions.UuidNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UuidService {

    private final TokenService tokenService;

    public UUID uuidGenerator() {

        return UUID.randomUUID();
    }

    public void validationUuid(Token tokenDB, UUID uuidQueryParam) {

        String token = tokenDB.getUuid().toString();
        String uuid = uuidQueryParam.toString();

        if (!token.equals(uuid) || !tokenDB.getType().name().equals(TokenType.EMAIL_VALIDATION.name())) throw new UuidNotValidException("Invalid UUID");

        if (!tokenDB.getExpirationDate().isAfter(LocalDateTime.now())) {
            tokenService.deleteTokenByEmail(tokenDB.getUser().getEmail());
            throw new UuidNotValidException("Expired UUID");
        }

    }

}

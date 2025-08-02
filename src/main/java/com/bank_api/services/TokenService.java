package com.bank_api.services;

import com.bank_api.entities.Token;
import com.bank_api.entities.User;
import com.bank_api.entities.enums.TokenType;
import com.bank_api.exceptions.EntityNotFoundException;
import com.bank_api.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private static final LocalDateTime EXPIRATION_DATE = LocalDateTime.now().plusMinutes(10);
    private final TokenRepository tokenRepository;

    @Transactional(readOnly = false)
    public void saveToken(UUID uuid, User user, TokenType type) {
        Token token = new Token();
        token.setUuid(uuid);
        token.setUser(user);
        token.setExpirationDate(EXPIRATION_DATE);
        token.setType(type);
        try {
            tokenRepository.save(token);
        }
        catch(DataIntegrityViolationException e) {
            log.error(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Token findUuidByEmail(String email) {
        return tokenRepository.findByUserEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Token not found")
        );
    }

    @Transactional(readOnly = false)
    public void deleteTokenByEmail(String email) {
        Token token = findUuidByEmail(email);
        tokenRepository.deleteById(token.getUuid());
    }

}

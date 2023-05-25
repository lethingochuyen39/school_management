package com.school.management.service;

import static com.school.management.config.SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.school.management.model.RefreshToken;
import com.school.management.repository.RefreshTokenRepository;
import com.school.management.repository.UserRepository;;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<RefreshToken> findByToken(String token) {

        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(String email) {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findByEmail(email).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION_TIME));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public class TokenRefreshException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TokenRefreshException(String token, String message) {
            super(String.format("Failed for [%s]: %s", token, message));
        }
    }

}

package com.school.management.service;

import java.util.Optional;

import com.school.management.model.RefreshToken;

public interface RefreshTokenService {
    public Optional<RefreshToken> findByToken(String token);

    public RefreshToken createRefreshToken(String email);

    public RefreshToken verifyExpiration(RefreshToken token);
}

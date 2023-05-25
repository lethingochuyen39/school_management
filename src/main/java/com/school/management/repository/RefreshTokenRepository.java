package com.school.management.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.school.management.model.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}

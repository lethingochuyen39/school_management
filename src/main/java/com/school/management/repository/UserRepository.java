package com.school.management.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.school.management.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

}

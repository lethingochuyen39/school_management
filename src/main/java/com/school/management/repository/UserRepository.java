package com.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

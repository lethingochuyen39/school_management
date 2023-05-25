package com.school.management.repository;

import org.springframework.data.repository.CrudRepository;

import com.school.management.model.Role;
import com.school.management.model.UserRole;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(UserRole role);
}

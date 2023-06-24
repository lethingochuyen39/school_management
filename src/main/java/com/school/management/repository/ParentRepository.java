package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.school.management.model.Parent;
import com.school.management.model.RefreshToken;
// import com.school.management.model.Teacher;
import com.school.management.model.User;

public interface ParentRepository extends CrudRepository<RefreshToken, Long>{
    @Query("SELECT u FROM Parent u WHERE u.user = :user")
    List<Parent> findByUser(@Param("user") User user);
}

package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.school.management.model.Classes;
import com.school.management.model.Student;
import com.school.management.model.User;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);

    // @Query("SELECT u FROM Student u WHERE u.user = :user")
    Student findByUser(User user);

    // huyen
    List<Student> findAllByClassNameId(Long classId);
}
package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// import com.school.management.model.Student;
import com.school.management.model.Teacher;
import com.school.management.model.User;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByNameContainingIgnoreCase(String name);


    // @Query("SELECT u FROM Teacher u WHERE u.user = :user")
    Teacher findByUser(User user);

    Teacher findByEmail(String string);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}

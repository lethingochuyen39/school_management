package com.school.management.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/student")
  @PreAuthorize("hasRole('STUDENT')")
  public String studentAccess() {
    return "student Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('SCHOOL') or hasRole('ADMIN')")
  public String modAccess() {
    return "Admin Content.";
  }

  @GetMapping("/school")
  @PreAuthorize("hasRole('SCHOOL')")
  public String schoolAccess() {
    return "School Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }

  @GetMapping("/parent")
  @PreAuthorize("hasRole('PARENT')")
  public String parentAccess() {
    return "Parent Board.";
  }
}

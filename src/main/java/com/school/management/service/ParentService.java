package com.school.management.service;

import java.util.List;

import com.school.management.dto.ParentDTO;


public interface ParentService {
    ParentDTO GetParent(String email);
    ParentDTO UpdateProfile(ParentDTO parent);
    List<ParentDTO> GetAllParent();
    String DeleteParent(String email);
    ParentDTO AddParent(ParentDTO parent);
    // Parent GiveAccessAccount(String email,Parent Parent);
    Long generateAccount();
}

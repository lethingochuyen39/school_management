package com.school.management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class ResetPassword {
    private String email;
    private String token;
}

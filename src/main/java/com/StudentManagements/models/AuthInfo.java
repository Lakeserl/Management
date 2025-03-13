package com.StudentManagements.models;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Date;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthInfo {
    private String token;
    private int failed = 0;
    private Date lastFailed;
}

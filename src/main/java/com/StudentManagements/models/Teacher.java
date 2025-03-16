package com.StudentManagements.models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher extends User{
    @NotBlank(message = "Cần nhập số điện thoại")
    private String phone;
    
    @PrePersist
    public void setRole(Role role) {
        this.role = Role.TEACHER;
    }
    
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
    
    
}

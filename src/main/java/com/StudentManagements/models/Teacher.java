package com.StudentManagements.models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher extends User{
    @NotBlank(message = "Cần nhập số điện thoại")
    @Pattern(regexp = "^(\\+84|84|0)[3|5|7|8|9][0-9]{8}$", message = "Số điện thoại không hợp lệ")
    @Column(nullable = false)
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

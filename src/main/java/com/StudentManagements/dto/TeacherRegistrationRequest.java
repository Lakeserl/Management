package com.StudentManagements.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TeacherRegistrationRequest {
    @NotBlank(message = "Vui lòng Nhập Họ tên")
    private String fullName;

    @NotBlank(message = "Vui lòng Nhập email")
    @Email(message = "Vui lòng nhập Email hợp lệ")
    private String email;

    @NotBlank(message = "Vui lòng nhập password")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;
    
    @NotBlank(message = "vui lòng nhập số điện thoại")
    private String phone;

	public String getFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
}
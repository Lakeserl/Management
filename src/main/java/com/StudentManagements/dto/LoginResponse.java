package com.StudentManagements.dto;


import com.StudentManagements.models.Role;
import lombok.Data;

@Data
public class LoginResponse {
    private String id;
    private String email;
    private String fullName;
    private Role role;
    private String token;
	public String getId() {
		return id;
	}
	public void setId(String string) {
		this.id = string;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
    
    
}
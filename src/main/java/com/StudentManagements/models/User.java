package com.StudentManagements.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    @Id
    private String id;

    @NotBlank(message = "Cần Nhập tên")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "Nhập email lệ")
    @Email(message = "Email phải hợp lệ")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Cần nhập password")
    @Size(min = 6, message = "mật khẩu phải có ít nhất ̉ký tự trở lên")
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
	protected Role role;

    @Embedded
    private AuthInfo auth = new AuthInfo();
    
    public void setRole(Role role) {
        this.role = role;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public AuthInfo getAuth() {
		return auth;
	}

	public void setAuth(AuthInfo auth) {
		this.auth = auth;
	}

	public Role getRole() {
		return role;
	}
    
    
}
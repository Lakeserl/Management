package com.StudentManagements.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
	@Id
    private String id;
    
    @NotBlank(message = "Cần nhập tên")
    @Pattern(regexp = "^(?!.*[0-9])(?!.*[!@#$%^&*()_+\\-=\\[\\]{};:\"\\\\|,.<>\\/?])[^\\n]*$", message= "Tên không phù hợp")
    private String fullName;
    
    @NotBlank(message = "Cần nhập email")
    @Email(message = "Email phải hợp lệ")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email không đúng định dạng")
    @Indexed(unique = true)
    private String email;
    
    @NotBlank(message = "Cần nhập password")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự trở lên")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#?!@$%^&*\\-.])(?!.*\\s).{6,}$", 
             message = "Mật khẩu phải có ít nhất 6 ký tự và một ký tự thường, 1 ký tự hoa, 1 số, 1 ký tự đặc biệt và không được có khoảng trắng")
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
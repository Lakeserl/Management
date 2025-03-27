package com.StudentManagements.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.Date;

@Document(collection = "students") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student extends User{
 	@NotBlank(message = "Cần nhập mã số sinh viên")
    @Indexed(unique = true)
    private String studentId;

    @NotBlank(message = "Cần nhập số điện thoại")
    @Pattern(regexp = "^(0[35789])+(\\d{8})\\b$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private Date dob;

    @NotBlank(message = "Cần nhập địa chỉ")
    @Pattern(regexp = "^[^\\n]*$", message = "Địa chỉ không hợp lệ")
    private String address;

    private int gender; // -1: Unknown, 0: Female, 1: Male

    private Date createdAt = new Date();

    @PrePersist
    public void prePersist() {
        this.role = Role.STUDENT;
        
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }
    public void setRole(Role role) {
        this.role = Role.STUDENT;
    }

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
    
}
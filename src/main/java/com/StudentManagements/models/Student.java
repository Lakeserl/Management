package com.StudentManagements.models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
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
    @Column(unique = true, nullable = false)
    private String studentId;

    @NotBlank(message = "Cần nhập số điện thoại")
    @Column(nullable = false)
    private String phone;

    @Temporal(TemporalType.DATE)
    private Date dob;

    private String address;

    private int gender; // -1: Unknown, 0: Female, 1: Male

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @PrePersist
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
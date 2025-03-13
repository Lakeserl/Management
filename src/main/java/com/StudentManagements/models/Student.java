package com.StudentManagements.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Temporal(TemporalType.DATE)
    private Date dob;

    private String address;

    private int gender; // -1: Unknown, 0: Female, 1: Male

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;

    @Embedded
    private AuthInfo auth = new AuthInfo();

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

	public Student(Long id, String studentId, String fullName, String email, String password, String phone, Date dob,
			String address, int gender, Role role, AuthInfo auth, Date createdAt) {
		this.id = id;
		this.studentId = studentId;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.dob = dob;
		this.address = address;
		this.gender = gender;
		this.role = role;
		this.auth = auth;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public AuthInfo getAuth() {
		return auth;
	}

	public void setAuth(AuthInfo auth) {
		this.auth = auth;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    
    
}

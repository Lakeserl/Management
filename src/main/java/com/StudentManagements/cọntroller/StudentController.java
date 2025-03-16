package com.StudentManagements.cọntroller;

import com.StudentManagements.dto.StudentRegistrationRequest;
import com.StudentManagements.dto.PasswordChangeRequest;
import com.StudentManagements.models.Student;
import com.StudentManagements.config.CurrentUser;
import com.StudentManagements.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody StudentRegistrationRequest request) {
        Student student = studentService.registerStudent(request);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Student> getStudentProfile(@CurrentUser String email) {
        Student student = studentService.getStudentByEmail(email);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Student> updateStudent(
            @PathVariable String id,
            @RequestBody StudentRegistrationRequest request,
            @CurrentUser String email) {
        Student student = studentService.updateStudent(id, request, email);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable String id,
            @CurrentUser String email) {
        studentService.deleteStudent(id, email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Void> changePassword(
            @PathVariable String id,
            @RequestBody PasswordChangeRequest request,
            @CurrentUser String email) {
        studentService.changePassword(id, request, email);
        return ResponseEntity.ok().build();
    }
}

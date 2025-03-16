package com.StudentManagements.cọntroller;

import com.StudentManagements.dto.TeacherRegistrationRequest;
import com.StudentManagements.dto.PasswordChangeRequest;
import com.StudentManagements.models.Student;
import com.StudentManagements.models.Teacher;
import com.StudentManagements.config.CurrentUser;
import com.StudentManagements.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/register")
    public ResponseEntity<Teacher> registerTeacher(@RequestBody TeacherRegistrationRequest request) {
        Teacher teacher = teacherService.registerTeacher(request);
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable String id) {
        Teacher teacher = teacherService.getTeacherById(id);
        return ResponseEntity.ok(teacher);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Teacher> updateTeacher(
            @PathVariable String id,
            @RequestBody TeacherRegistrationRequest request,
            @CurrentUser String email) {
        Teacher teacher = teacherService.updateTeacher(id, request, email);
        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> deleteTeacher(
            @PathVariable String id,
            @CurrentUser String email) {
        teacherService.deleteTeacher(id, email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = teacherService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/{id}/change-password")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> changePassword(
            @PathVariable String id,
            @RequestBody PasswordChangeRequest request,
            @CurrentUser String email) {
        teacherService.changePassword(id, request, email);
        return ResponseEntity.ok().build();
    }
}
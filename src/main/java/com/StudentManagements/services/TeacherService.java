
package com.StudentManagements.services;

import com.StudentManagements.dto.TeacherRegistrationRequest;
import com.StudentManagements.dto.PasswordChangeRequest;
import com.StudentManagements.Exceptions.DuplicateResourceException;
import com.StudentManagements.Exceptions.ResourceNotFoundException;
import com.StudentManagements.Exceptions.UnauthorizedException;
import com.StudentManagements.models.Student;
import com.StudentManagements.models.Teacher;
import com.StudentManagements.repository.TeacherRepository;
import com.StudentManagements.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Teacher registerTeacher(TeacherRegistrationRequest request) {
        if (teacherRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email đã tồn tại");
        }
        
        Teacher teacher = new Teacher();
        teacher.setFullName(request.getFullName());
        teacher.setEmail(request.getEmail());
        teacher.setPassword(passwordEncoder.encode(request.getPassword()));
        teacher.setPhone(request.getPhone());
        
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(String id, TeacherRegistrationRequest request, String currentUserEmail) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên có id: " + id));
        
        if (!teacher.getEmail().equals(currentUserEmail)) {
            throw new UnauthorizedException("Bạn chỉ có thể cập nhật hồ sơ của chính bạn");
        }
        
        if (!teacher.getEmail().equals(request.getEmail()) && 
            teacherRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email đã được sử dụng");
        }
        
        teacher.setFullName(request.getFullName());
        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());
        
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(String id, String currentUserEmail) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên có id: " + id));
        
        if (!teacher.getEmail().equals(currentUserEmail)) {
            throw new UnauthorizedException("Bạn chỉ có thể xoá profile của chính bạn");
        }
        
        teacherRepository.delete(teacher);
    }

    public Teacher getTeacherById(String id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + id));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void changePassword(String id, PasswordChangeRequest request, String currentUserEmail) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + id));
        
        if (!teacher.getEmail().equals(currentUserEmail)) {
            throw new UnauthorizedException("You can only change your own password");
        }
        
        if (!passwordEncoder.matches(request.getCurrentPassword(), teacher.getPassword())) {
            throw new UnauthorizedException("Current password is incorrect");
        }
        
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new UnauthorizedException("New password and confirm password do not match");
        }
        
        teacher.setPassword(passwordEncoder.encode(request.getNewPassword()));
        teacherRepository.save(teacher);
    }
}
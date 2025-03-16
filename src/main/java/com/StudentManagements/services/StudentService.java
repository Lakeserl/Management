package com.StudentManagements.services;

import com.StudentManagements.dto.StudentRegistrationRequest;
import com.StudentManagements.dto.PasswordChangeRequest;
import com.StudentManagements.Exceptions.DuplicateResourceException;
import com.StudentManagements.Exceptions.ResourceNotFoundException;
import com.StudentManagements.Exceptions.UnauthorizedException;
import com.StudentManagements.models.Student;
import com.StudentManagements.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Student registerStudent(StudentRegistrationRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email đã được sử dụng");
        }

        if (studentRepository.findByStudentId(request.getStudentId()).isPresent()) {
            throw new DuplicateResourceException("Mã số sv đã tồn tại");
        }
        
        Student student = new Student();student.setStudentId(request.getStudentId());
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setPhone(request.getPhone());
        student.setDob(request.getDob());
        student.setAddress(request.getAddress());
        student.setGender(request.getGender());
        
        return studentRepository.save(student);
    }

    public Student updateStudent(String id, StudentRegistrationRequest request, String currentUserEmail) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tồn tại học sinh có id: " + id));

        if (!student.getEmail().equals(currentUserEmail)) {
            throw new UnauthorizedException("Bạn chỉ có thể cập nhật hồ sơ của riêng bạn");
        }

        if (!student.getEmail().equals(request.getEmail()) && 
            studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email đã tồn tại ");
        }

        if (!student.getStudentId().equals(request.getStudentId()) && 
            studentRepository.findByStudentId(request.getStudentId()).isPresent()) {
            throw new DuplicateResourceException("Student ID đã được sử dụng");
        }
        
        student.setStudentId(request.getStudentId());
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDob(request.getDob());
        student.setAddress(request.getAddress());
        student.setGender(request.getGender());
        
        return studentRepository.save(student);
    }

    public void deleteStudent(String id, String currentUserEmail) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sinh viên có id : " + id));
        
        // Check if the current user is deleting their own profile
        if (!student.getEmail().equals(currentUserEmail)) {
            throw new UnauthorizedException("Bạn chỉ có thể xóa hồ sơ của riêng bạn");
        }
        
        studentRepository.delete(student);
    }

    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học sinh có id : " + id));
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học sinh có email: " + email));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void changePassword(String id, PasswordChangeRequest request, String currentUserEmail) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học sinh có id : " + id));
        
        if (!student.getEmail().equals(currentUserEmail)) {
            throw new UnauthorizedException("Bạn chỉ có thể thay đổi mật khẩu của chính con mm");
        }
        
        if (!passwordEncoder.matches(request.getCurrentPassword(), student.getPassword())) {
            throw new UnauthorizedException("Mật khẩu hiện tại không chính xác");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new UnauthorizedException("Mật khẩu mới và xác nhận mật khẩu không khớp");
        }
        
        student.setPassword(passwordEncoder.encode(request.getNewPassword()));
        studentRepository.save(student);
    }
}
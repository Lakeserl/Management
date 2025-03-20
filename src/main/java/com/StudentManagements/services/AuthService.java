package com.StudentManagements.services;

import com.StudentManagements.dto.LoginRequest;
import com.StudentManagements.dto.LoginResponse;
import com.StudentManagements.dto.LogoutResponse;
import com.StudentManagements.models.AuthInfo;
import com.StudentManagements.models.Role;
import com.StudentManagements.models.Student;
import com.StudentManagements.models.Teacher;
import com.StudentManagements.repository.StudentRepository;
import com.StudentManagements.repository.TeacherRepository;
import com.StudentManagements.config.JwtProvider;
import com.StudentManagements.Exceptions.AccountLockedException;
import com.StudentManagements.Exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        if ("STUDENT".equals(loginRequest.getRole())) {
            return loginStudent(loginRequest);
        } else if ("TEACHER".equals(loginRequest.getRole())) {
            return loginTeacher(loginRequest);
        } else {
            throw new UnauthorizedException("DELL có role này");
        }
    }

    private LoginResponse loginStudent(LoginRequest loginRequest) {
        Optional<Student> studentOpt = studentRepository.findByEmail(loginRequest.getEmail());
        
        if (studentOpt.isEmpty()) {
            throw new UnauthorizedException("Sai email hoặc password");
        }
        
        Student student = studentOpt.get();

        if (!student.getAuth().isAccountNonLocked()) {
            if (unlockWhenTimeExpired(student)) {
                studentRepository.save(student);
            } else {
                throw new AccountLockedException("Tài khoản của bạn đã bị khóa do đăng nhập sai những 3 lần. " +
                        "Bạn có 24 giờ không truy cập được vào tài khoản :V");
            }
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), student.getPassword())) {
            increaseFailedAttempts(student);
            studentRepository.save(student);
            throw new UnauthorizedException("Sai email hoặc password");
        }

        resetFailedAttempts(student);
        studentRepository.save(student);

        String token = jwtTokenProvider.generateToken(student.getEmail(), student.getRole());

        LoginResponse response = new LoginResponse();
        response.setId(student.getId());
        response.setEmail(student.getEmail());
        response.setFullName(student.getFullName());
        response.setRole(student.getRole());
        response.setToken(token);
        
        return response;
    }

    private LoginResponse loginTeacher(LoginRequest loginRequest) {
        Optional<Teacher> teacherOpt = teacherRepository.findByEmail(loginRequest.getEmail());
        
        if (teacherOpt.isEmpty()) {
            throw new UnauthorizedException("Sai Email hoặc password");
        }
        
        Teacher teacher = teacherOpt.get();
        
        if (!teacher.getAuth().isAccountNonLocked()) {
            if (unlockWhenTimeExpired(teacher)) {
                teacherRepository.save(teacher);
            } else {
                throw new AccountLockedException("Tài khoản của bạn đã bị khóa do đăng nhập sai những 3 lần. " +
                        "Bạn có 24 giờ không truy cập được vào tài khoản :V");
            }
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), teacher.getPassword())) {
            increaseFailedAttempts(teacher);
            teacherRepository.save(teacher);
            throw new UnauthorizedException("Sai Email hoặc password");
        }
        
        resetFailedAttempts(teacher);
        teacherRepository.save(teacher);
        

        String token = jwtTokenProvider.generateToken(teacher.getEmail(), teacher.getRole());
        
        LoginResponse response = new LoginResponse();
        response.setId(teacher.getId());
        response.setEmail(teacher.getEmail());
        response.setFullName(teacher.getFullName());
        response.setRole(teacher.getRole());
        response.setToken(token);
        
        return response;
    }

    private void increaseFailedAttempts(Student student) {
        AuthInfo auth = student.getAuth();
        int newFailAttempts = auth.getFailedAttempts() + 1;
        auth.setFailedAttempts(newFailAttempts);
        
        if (newFailAttempts >= MAX_FAILED_ATTEMPTS) {
            auth.setAccountNonLocked(false);
            auth.setLockTime(new Date());
        }
    }

    private void increaseFailedAttempts(Teacher teacher) {
        AuthInfo auth = teacher.getAuth();
        int newFailAttempts = auth.getFailedAttempts() + 1;
        auth.setFailedAttempts(newFailAttempts);
        
        if (newFailAttempts >= MAX_FAILED_ATTEMPTS) {
            auth.setAccountNonLocked(false);
            auth.setLockTime(new Date());
        }
    }

    private void resetFailedAttempts(Student student) {
        AuthInfo auth = student.getAuth();
        auth.setFailedAttempts(0);
    }

    private void resetFailedAttempts(Teacher teacher) {
        AuthInfo auth = teacher.getAuth();
        auth.setFailedAttempts(0);
    }

    private boolean unlockWhenTimeExpired(Student student) {
        AuthInfo auth = student.getAuth();
        
        if (auth.getLockTime() == null) return false;
        long lockTimeInMillis = auth.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        
        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            auth.setAccountNonLocked(true);
            auth.setLockTime(null);
            auth.setFailedAttempts(0);
            return true;
        }
        
        return false;
    }

    private boolean unlockWhenTimeExpired(Teacher teacher) {
        AuthInfo auth = teacher.getAuth();
        if (auth.getLockTime() == null) return false;
        
        long lockTimeInMillis = auth.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        
        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            auth.setAccountNonLocked(true);
            auth.setLockTime(null);
            auth.setFailedAttempts(0);
            return true;
        }
        
        return false;
    }
    
    public LogoutResponse logout() {
        return new LogoutResponse("Đăng xuất thành công", true);
    }
}
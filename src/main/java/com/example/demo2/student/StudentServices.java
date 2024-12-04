package com.example.demo2.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentServices {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServices(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student){
       Optional<Student> studentOptional =  studentRepository.findStudentByEmail(student.getEmail());
       if(studentOptional.isPresent()){
           throw new IllegalStateException("email taken");
       }
       studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists =  studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("student with id" + studentId + "does not exists");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, Student studentDetails) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with ID " + studentId + " doesn't exist"));

        if (studentDetails.getName() != null && studentDetails.getName().length() > 0 && !studentDetails.getName().equals(student.getName())) {
            student.setName(studentDetails.getName());
        }

        if (studentDetails.getEmail() != null && studentDetails.getEmail().length() > 0 && !studentDetails.getEmail().equals(student.getEmail())) {
            Optional<Student> studentWithEmail = studentRepository.findStudentByEmail(studentDetails.getEmail());
            if (studentWithEmail.isPresent()) {
                throw new IllegalStateException("Email already taken");
            }
            student.setEmail(studentDetails.getEmail());
        }
    }

}

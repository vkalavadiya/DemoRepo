package com.example.demo2.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping(path= "api/v1/student")
public class StudentController {

    private final StudentServices studentServices;

    @Autowired
    public StudentController(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentServices.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        studentServices.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentServices.deleteStudent(studentId);
    }

    @PutMapping("/{studentId}")
    @Transactional
    public ResponseEntity<String> updateStudent(@PathVariable("studentId") Long studentId,
                                                @RequestBody Student studentDetails) {
        try {
            studentServices.updateStudent(studentId, studentDetails);
            return ResponseEntity.status(HttpStatus.OK).body("Student updated successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

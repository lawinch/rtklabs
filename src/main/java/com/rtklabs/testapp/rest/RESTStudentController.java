package com.rtklabs.testapp.rest;

import com.rtklabs.testapp.model.Student;
import com.rtklabs.testapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_XML_VALUE)
@XmlRootElement(name = "students")
public class RESTStudentController {
    @Autowired
	StudentRepository studentRepository;

    // Get All Students
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Create a new Student
    @PostMapping("/students")
    public Student createStudent(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }

    // Get a Single Student
    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable(value = "id") Long studentId) {
        return studentRepository.findOne(studentId);
    }

    // Update a Student
    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable(value = "id") Long studentId,
                                 @Valid @RequestBody Student studentDetails) {
        Student student = studentRepository.findOne(studentId);
        student.setName(studentDetails.getName());
        student.setSurname(studentDetails.getSurname());
        student.setInstructors(studentDetails.getInstructors());
		student.setCourses(studentDetails.getCourses());
        Student updatedStudent = studentRepository.save(student);
        return updatedStudent;
    }

    // Delete a Student
    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long studentId) {
        Student student = studentRepository.findOne(studentId);
        studentRepository.delete(student);
        return ResponseEntity.ok().build();
    }
}

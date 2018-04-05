package com.rtklabs.testapp.rest;

import com.rtklabs.testapp.model.Instructor;
import com.rtklabs.testapp.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_XML_VALUE)
public class RESTInstructorController {
    @Autowired
	InstructorRepository instructorRepository;

    // Get All Instructors
    @GetMapping("/instructors")
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    // Create a new Instructor
    @PostMapping("/instructors")
    public Instructor createInstructor(@Valid @RequestBody Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    // Get a Single Instructor
    @GetMapping("/instructors/{id}")
    public Instructor getInstructorById(@PathVariable(value = "id") Long instructorId) {
        return instructorRepository.findOne(instructorId);
    }

    // Update a Instructor
    @PutMapping("/instructors/{id}")
    public Instructor updateInstructor(@PathVariable(value = "id") Long instructorId,
                                 @Valid @RequestBody Instructor instructorDetails) {
    	Instructor instructor = instructorRepository.findOne(instructorId);
        instructor.setName(instructorDetails.getName());
        instructor.setSurname(instructorDetails.getSurname());
        instructor.setStudents(instructorDetails.getStudents());
        instructor.setCourses(instructorDetails.getCourses());
        Instructor updatedInstructor = instructorRepository.save(instructor);
        return updatedInstructor;
    }

    // Delete an Instructor
    @DeleteMapping("/instructors/{id}")
    public ResponseEntity<?> deleteInstructor(@PathVariable(value = "id") Long instructorId) {
        Instructor instructor = instructorRepository.findOne(instructorId);
        instructorRepository.delete(instructor);
        return ResponseEntity.ok().build();
    }
}

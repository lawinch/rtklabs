package com.rtklabs.testapp.rest;

import com.rtklabs.testapp.model.Course;
import com.rtklabs.testapp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_XML_VALUE)
@XmlRootElement(name = "courses")
public class RESTCourseController {
    @Autowired
	CourseRepository courseRepository;

    // Get All Courses
    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Create a new Course
    @PostMapping("/courses")
    public Course createCourse(@Valid @RequestBody Course course) {
        return courseRepository.save(course);
    }

    // Get a Single Course
    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable(value = "id") Long courseId) {
        return courseRepository.findOne(courseId);
    }

    // Update a Course
    @PutMapping("/courses/{id}")
    public Course updateCourse(@PathVariable(value = "id") Long courseId,
                                 @Valid @RequestBody Course courseDetails) {
        Course course = courseRepository.findOne(courseId);
		course.setName(courseDetails.getName());
		course.setInstructors(courseDetails.getInstructors());
		course.setStudents(courseDetails.getStudents());
        Course updatedCourse = courseRepository.save(course);
        return updatedCourse;
    }

    // Delete a Course
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") Long courseId) {
        Course course = courseRepository.findOne(courseId);
        courseRepository.delete(course);
        return ResponseEntity.ok().build();
    }
}

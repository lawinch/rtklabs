package com.rtklabs.testapp.controller;

import com.rtklabs.testapp.model.Course;
import com.rtklabs.testapp.model.Instructor;
import com.rtklabs.testapp.model.Student;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Scope (value = "session")
@Component (value = "listStudents")
@ELBeanName(value = "listStudents")
@ManagedBean(name = "listStudents")
public class ListStudentsController implements Serializable {

	public static final String REST_SERVICE_URI = "http://localhost:8080/api/students/";

	private List<Student> students;

	public List<Student> getStudents() {
		return students;
	}

	public String delete(Student student) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI+student.getId());
		loadRestData();
		return null;
	}

	public void loadRestData(){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		ResponseEntity<Student[]> response = restTemplate.exchange(
				REST_SERVICE_URI, HttpMethod.GET, entity, Student[].class);
		students = Arrays.asList(response.getBody());
	}


	public void save(Student student){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		HttpEntity<Student> request = new HttpEntity<>(student, headers);

		restTemplate.postForLocation(REST_SERVICE_URI, request, Student.class);
	}

	public void update(Student student)
	{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Student> request = new HttpEntity<>(student, headers);
		headers.setContentType(MediaType.APPLICATION_XML);
		//Внимание, костыль решающий бесконечную вложенность
		for(Instructor instructor: student.getInstructors()) {
			instructor.setStudents(new HashSet<>());
			instructor.setCourses(new HashSet<>());
		}
		for(Course course: student.getCourses()) {
			course.setStudents(new HashSet<>());
			course.setInstructors(new HashSet<>());
		}
		restTemplate.put(REST_SERVICE_URI + student.getId(), request);
	}

}

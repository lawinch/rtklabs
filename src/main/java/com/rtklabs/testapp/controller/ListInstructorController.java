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
@Component (value = "listInstructors")
@ELBeanName(value = "listInstructors")
@ManagedBean(name = "listInstructors")
public class ListInstructorController implements Serializable {
	public static final String REST_SERVICE_URI = "http://localhost:8080/api/instructors/";

	private List<Instructor> instructors;


	public List<Instructor> getInstructors() {
		return instructors;
	}


	public void loadRestData(){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<Instructor[]> response = restTemplate.exchange(
				REST_SERVICE_URI, HttpMethod.GET, entity, Instructor[].class);
		instructors = Arrays.asList(response.getBody());
	}

	public void delete(Instructor instructor) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI+instructor.getId());
		loadRestData();
	}

	public void save(Instructor instructor){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		HttpEntity<Instructor> request = new HttpEntity<>(instructor, headers);

		restTemplate.postForLocation(REST_SERVICE_URI, request, Instructor.class);
	}

	public void update(Instructor instructor)
	{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Instructor> request = new HttpEntity<Instructor>(instructor, headers);
		headers.setContentType(MediaType.APPLICATION_XML);
		//Внимание, костыль решающий бесконечную вложенность
		for(Student s: instructor.getStudents()) {
			s.setInstructors(new HashSet<>());
			s.setCourses(new HashSet<>());
		}
		for(Course c: instructor.getCourses()) {
			c.setInstructors(new HashSet<>());
			c.setStudents(new HashSet<>());
		}
		restTemplate.put(REST_SERVICE_URI + instructor.getId(), request);
	}
}

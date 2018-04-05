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
@Component (value = "listCourses")
@ELBeanName(value = "listCourses")
@ManagedBean(name = "listCourses")
public class ListCoursesController implements Serializable {
	public static final String REST_SERVICE_URI = "http://localhost:8080/api/courses/";

	private List<Course> courses;


	public List<Course> getCourses() {
		return courses;
	}


	public void loadRestData(){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<Course[]> response = restTemplate.exchange(
				REST_SERVICE_URI, HttpMethod.GET, entity, Course[].class);
		courses = Arrays.asList(response.getBody());
	}

	public void delete(Course course) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI+course.getId());
		loadRestData();
	}

	public void save(Course course){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		HttpEntity<Course> request = new HttpEntity<>(course, headers);

		restTemplate.postForLocation(REST_SERVICE_URI, request, Course.class);
	}

	public void update(Course course)
	{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Course> request = new HttpEntity<>(course, headers);
		headers.setContentType(MediaType.APPLICATION_XML);
		//Внимание, костыль решающий бесконечную вложенность
		for(Student s: course.getStudents()) {
			s.setCourses(new HashSet<>());
			s.setInstructors(new HashSet<>());
		}
		for(Instructor i: course.getInstructors()) {
			i.setCourses(new HashSet<>());
			i.setStudents(new HashSet<>());
		}

		restTemplate.put(REST_SERVICE_URI + course.getId(), request);
	}
}

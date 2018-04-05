package com.rtklabs.testapp.controller;

import com.rtklabs.testapp.model.Course;
import com.rtklabs.testapp.model.Instructor;
import com.rtklabs.testapp.model.Student;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Scope(value = "session")
@Component(value = "instructorController")
@ELBeanName(value = "instructorController")
@Join(path = "/instructor", to = "/instructor/instructor-form.jsf")
@ManagedBean(name = "instructorController")
public class FormInstructorController {

	@Autowired
	private ListStudentsController listStudentsController;

	@Autowired
	private ListInstructorController listInstructorController;

	@Autowired
	private ListCoursesController listCoursesController;

	private List<Student> students;
	private List<Course> courses;

	private List<Long> selectedStudents;
	private List<Long> selectedCourses;

	public List<Course> getCourses() {
		listCoursesController.loadRestData();
		courses = listCoursesController.getCourses();
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Long> getSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(List<Long> selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	public List<Long> getSelectedStudents() {
		return selectedStudents;
	}

	public void setSelectedStudents(List<Long> selectedStudents) {
		this.selectedStudents = selectedStudents;
	}


	private Instructor instructor = new Instructor();
	private String action = "create";

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String save() {
		if(action.equals("create")) {
			return create();
		} else if (action.equals("edit")) {
			return update();
		} else {
			return null;
		}
	}

	public String create() {
		List<Student> studentList = students.stream().filter(
				s -> selectedStudents.contains(s.getId())).collect(Collectors.toList());
		List<Course> courseList = courses.stream().filter(
				s -> selectedCourses.contains(s.getId())).collect(Collectors.toList());
		instructor.setStudents(new HashSet<>(studentList));
		instructor.setCourses(new HashSet<>(courseList));
		listInstructorController.save(instructor);
		instructor = new Instructor();
		action = "create";
		selectedStudents.clear();
		selectedCourses.clear();
		return "/index.xhtml?faces-redirect=true";
	}

	public String update() {
		List<Student> studentList = students.stream().filter(
				s -> selectedStudents.contains(s.getId())).collect(Collectors.toList());
		List<Course> courseList = courses.stream().filter(
				s -> selectedCourses.contains(s.getId())).collect(Collectors.toList());
		instructor.setStudents(new HashSet<>(studentList));
		instructor.setCourses(new HashSet<>(courseList));
		listInstructorController.update(instructor);
		instructor = new Instructor();
		action = "create";
		selectedStudents.clear();
		selectedCourses.clear();
		return "/index.xhtml?faces-redirect=true";
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public List<Student> getStudents() {
		listStudentsController.loadRestData();
		students = listStudentsController.getStudents();
		return students;
	}

	//not working :(
	public void clear()
	{
		if(action.equals("create"))
			setInstructor(new Instructor());
	}

}

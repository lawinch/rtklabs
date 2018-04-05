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
@Component(value = "studentController")
@ELBeanName(value = "studentController")
@Join(path = "/student", to = "/student/student-form.jsf")
@ManagedBean(name = "studentController")
public class FormStudentController {
	@Autowired
	private ListStudentsController listStudentsController;

	@Autowired
	private ListInstructorController listInstructorController;

	@Autowired
	private ListCoursesController listCoursesController;

	private List<Instructor> instructors;
	private List<Course> courses;

	private List<Long> selectedInstructors;
	private List<Long> selectedCourses;

	public List<Long> getSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(List<Long> selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	public List<Long> getSelectedInstructors() {
		return selectedInstructors;
	}

	public void setSelectedInstructors(List<Long> selectedStudents) {
		this.selectedInstructors = selectedStudents;
	}



	private Student student = new Student();
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
		List<Instructor> instructorList = instructors.stream().filter(
				s -> selectedInstructors.contains(s.getId())).collect(Collectors.toList());
		List<Course> courseList = courses.stream().filter(
				s -> selectedCourses.contains(s.getId())).collect(Collectors.toList());
		student.setInstructors(new HashSet<>(instructorList));
		student.setCourses(new HashSet<>(courseList));
		listStudentsController.save(student);
		student = new Student();
		action = "create";
		selectedInstructors.clear();
		selectedCourses.clear();
		return "/index.xhtml?faces-redirect=true";
	}

	public String update() {
		List<Instructor> instructorList = instructors.stream().filter(
				s -> selectedInstructors.contains(s.getId())).collect(Collectors.toList());
		List<Course> courseList = courses.stream().filter(
				s -> selectedCourses.contains(s.getId())).collect(Collectors.toList());
		student.setInstructors(new HashSet<>(instructorList));
		student.setCourses(new HashSet<>(courseList));
		listStudentsController.update(student);
		student = new Student();
		action = "create";
		selectedInstructors.clear();
		selectedCourses.clear();
		return "/index.xhtml?faces-redirect=true";
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Instructor> getInstructors() {
		listInstructorController.loadRestData();
		instructors = listInstructorController.getInstructors();
		return instructors;
	}

	public void setInstructors(List<Instructor> instructors) {
		this.instructors = instructors;
	}

	public List<Course> getCourses() {
		listCoursesController.loadRestData();
		courses = listCoursesController.getCourses();
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	//not working :(
	public void clear()
	{
		if(action.equals("create"))
			setStudent(new Student());
	}
}

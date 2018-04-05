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
@Component(value = "courseController")
@ELBeanName(value = "courseController")
@Join(path = "/course", to = "/course/course-form.jsf")
@ManagedBean(name = "courseController")
public class FormCourseController {
	@Autowired
	private ListStudentsController listStudentsController;

	@Autowired
	private ListInstructorController listInstructorController;

	@Autowired
	private ListCoursesController listCoursesController;

	private List<Instructor> instructors;
	private List<Student> students;

	private List<Long> selectedInstructors;
	private List<Long> selectedStudents;

	public List<Long> getSelectedInstructors() {
		return selectedInstructors;
	}

	public void setSelectedInstructors(List<Long> selectedStudents) {
		this.selectedInstructors = selectedStudents;
	}

	public List<Long> getSelectedStudents() {
		return selectedStudents;
	}

	public void setSelectedStudents(List<Long> selectedStudents) {
		this.selectedStudents = selectedStudents;
	}

	private Course course = new Course();
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
		List<Student> studentList = students.stream().filter(
				s -> selectedStudents.contains(s.getId())).collect(Collectors.toList());
		course.setInstructors(new HashSet<>(instructorList));
		course.setStudents(new HashSet<>(studentList));
		listCoursesController.save(course);
		course = new Course();
		action = "create";
		selectedInstructors.clear();
		selectedStudents.clear();
		return "/index.xhtml?faces-redirect=true";
	}

	public String update() {
		List<Instructor> instructorList = instructors.stream().filter(
				s -> selectedInstructors.contains(s.getId())).collect(Collectors.toList());
		List<Student> studentList = students.stream().filter(
				s -> selectedStudents.contains(s.getId())).collect(Collectors.toList());
		course.setInstructors(new HashSet<>(instructorList));
		course.setStudents(new HashSet<>(studentList));
		listCoursesController.update(course);
		course = new Course();
		action = "create";
		selectedInstructors.clear();
		selectedStudents.clear();
		return "/index.xhtml?faces-redirect=true";
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Instructor> getInstructors() {
		listInstructorController.loadRestData();
		instructors = listInstructorController.getInstructors();
		return instructors;
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
			setCourse(new Course());
	}
}

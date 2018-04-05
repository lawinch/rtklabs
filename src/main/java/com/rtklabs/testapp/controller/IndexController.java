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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Scope(value = "session")
@Component(value = "indexController")
@ELBeanName(value = "indexController")
@Join(path = "/", to = "/index.jsf")
@ManagedBean(name = "indexController")
public class IndexController  implements Serializable {

	@Autowired
	private ListInstructorController listInstructorController;

	@Autowired
	private ListStudentsController listStudentsController;

	@Autowired
	private FormInstructorController formInstructorController;

	@Autowired
	private FormStudentController formStudentController;

	@Autowired
	private ListCoursesController listCoursesController;

	@Autowired
	private FormCourseController formCourseController;

	private List<Instructor> instructors;
	private List<Student> students;
	private List<Course> courses;

	public List<Course> getCourses() {
		listCoursesController.loadRestData();
		courses = listCoursesController.getCourses();
		return courses;
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

	public String delete(Instructor instructor) {
		listInstructorController.delete(instructor);
		return null;
	}

	public String delete(Student student) {
		listStudentsController.delete(student);
		return null;
	}

	public String delete(Course course) {
		listCoursesController.delete(course);
		return null;
	}

	public String edit(Student student) {
		formStudentController.setStudent(student);
		formStudentController.setAction("edit");
		List<Long> inst_ids = new ArrayList<>();
		List<Long> course_ids = new ArrayList<>();
		if(student.getInstructors() != null)
			for(Instructor i: student.getInstructors())
				inst_ids.add(i.getId());
		if(student.getCourses() != null)
			for (Course c: student.getCourses())
				course_ids.add(c.getId());
		formStudentController.setSelectedInstructors(inst_ids);
		formStudentController.setSelectedCourses(course_ids);
		return "/student/student-form.xhtml?faces-redirect=true";
	}

	public String edit(Instructor instructor) {
		formInstructorController.setInstructor(instructor);
		formInstructorController.setAction("edit");
		List<Long> stud_ids = new ArrayList<>();
		List<Long> course_ids = new ArrayList<>();
		if(instructor.getStudents() != null)
			for (Student s : instructor.getStudents())
				stud_ids.add(s.getId());
		if(instructor.getCourses() != null)
			for (Course c: instructor.getCourses())
				course_ids.add(c.getId());
		formInstructorController.setSelectedStudents(stud_ids);
		formInstructorController.setSelectedCourses(course_ids);
		return "/instructor/instructor-form.xhtml?faces-redirect=true";
	}

	public String edit(Course course) {
		formCourseController.setCourse(course);
		formCourseController.setAction("edit");
		List<Long> stud_ids = new ArrayList<>();
		List<Long> inst_ids = new ArrayList<>();
		if(course.getStudents() != null)
			for (Student s : course.getStudents())
				stud_ids.add(s.getId());
		if(course.getInstructors() != null) {
			for (Instructor i : course.getInstructors())
				inst_ids.add(i.getId());
		}
		formCourseController.setSelectedStudents(stud_ids);
		formCourseController.setSelectedInstructors(inst_ids);
		return "/course/course-form.xhtml?faces-redirect=true";
	}


}

package com.rtklabs.testapp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import java.util.*;

@Entity
@Table(name = "students")
@EntityListeners(AuditingEntityListener.class)
@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String surname;

	@JsonSerialize(as=Collection.class, contentUsing = InstructorSerializer.class)
//	@JsonIgnoreProperties({"students", "courses"})
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "students_instructors",
			joinColumns = {@JoinColumn(name = "student_id")},
			inverseJoinColumns = {@JoinColumn(name = "instructor_id")}
	)
	private Set<Instructor> instructors = new HashSet<>();

	@JsonSerialize(as=Collection.class, contentUsing = CourseSerializer.class)
//	@JsonIgnoreProperties({"students", "instructors"})
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "students_courses",
			joinColumns = {@JoinColumn(name = "student_id")},
			inverseJoinColumns = {@JoinColumn(name = "course_id")}
	)
	private Set<Course> courses = new HashSet<>();

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@JsonSerialize(as=Collection.class, contentUsing = InstructorSerializer.class)
//	@JsonIgnoreProperties({"students", "courses"})
	public Set<Instructor> getInstructors() {
		return instructors;
	}

	public void setInstructors(Set<Instructor> instructors) {
		this.instructors = instructors;
	}

	@JsonSerialize(as=Collection.class, contentUsing = CourseSerializer.class)
//	@JsonIgnoreProperties({"instructors", "students"})
	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
}

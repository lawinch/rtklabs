package com.rtklabs.testapp.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.*;

@Entity
@Table(name = "instructors")
@EntityListeners(AuditingEntityListener.class)
@JsonRootName(value = "instructor")
@XmlAccessorType(XmlAccessType.FIELD)
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id")

public class Instructor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String surname;

	@JsonSerialize(as=Collection.class, contentUsing = StudentSerializer.class)
//	@JsonIgnoreProperties({"courses", "instructors"})
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "students_instructors",
			joinColumns = {@JoinColumn(name = "instructor_id")},
			inverseJoinColumns = {@JoinColumn(name = "student_id")}
	)
	private Set<Student> students = new HashSet<>();


	@JsonSerialize(as=Collection.class, contentUsing = CourseSerializer.class)
//	@JsonIgnoreProperties({"students", "instructors", "name"})
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "instructor_courses",
			joinColumns = {@JoinColumn(name = "instructor_id")},
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

	@JsonSerialize(as=Collection.class, contentUsing = StudentSerializer.class)
//	@JsonIgnoreProperties({"courses", "instructors", "name", "surname"})
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	@JsonSerialize(as=Collection.class, contentUsing = CourseSerializer.class)
//	@JsonIgnoreProperties({"students", "instructors", "name"})
	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
}

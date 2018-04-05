package com.rtklabs.testapp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@EntityListeners(AuditingEntityListener.class)
@XmlRootElement(name = "course")
@XmlAccessorType(XmlAccessType.FIELD)
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id")

public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String name;

	@JsonSerialize(as=Collection.class, contentUsing = StudentSerializer.class)
//	@JsonIgnoreProperties({"courses", "instructors"})
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "students_courses",
			joinColumns = {@JoinColumn(name = "course_id")},
			inverseJoinColumns = {@JoinColumn(name = "student_id")}
	)
	private Set<Student> students = new HashSet<>();

	@JsonSerialize(as=Collection.class, contentUsing = InstructorSerializer.class)
//	@JsonIgnoreProperties({"students", "courses"})
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "instructor_courses",
			joinColumns = {@JoinColumn(name = "course_id")},
			inverseJoinColumns = {@JoinColumn(name = "instructor_id")}
	)
	private Set<Instructor> instructors = new HashSet<>();

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonSerialize(as=Collection.class, contentUsing = StudentSerializer.class)
//	@JsonIgnoreProperties({"courses", "instructors"})
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	@JsonSerialize(as=Collection.class, contentUsing = InstructorSerializer.class)
//	@JsonIgnoreProperties({"students", "courses"})
	public Set<Instructor> getInstructors() {
		return instructors;
	}

	public void setInstructors(Set<Instructor> instructors) {
		this.instructors = instructors;
	}
}

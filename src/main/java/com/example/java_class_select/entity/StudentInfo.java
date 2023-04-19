package com.example.java_class_select.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "studentinfo")
public class StudentInfo {

	// 學生ID
	@Id
	@Column(name = "studentid")
	private String studentId;
	
	// 學生姓名
	@Column(name = "name")
	private String name;
	
	// 學生學分
	@Column(name = "credits")
	private int credits;
	
	// 學生已選課ID
	@Column(name = "courseid")
	private String courseId;
	
	// 學生已選課名稱
	@Column(name = "coursename")
	private String courseName;
	
	public StudentInfo() {
	}
	
	public StudentInfo(String studentId, String name) {
		this.studentId = studentId;
		this.name = name;
	}
	
	public StudentInfo(String studentId, String name, int credits) {
		this.studentId = studentId;
		this.name = name;
		this.credits = credits;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	
	

	
	
	
}

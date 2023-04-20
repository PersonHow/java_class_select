 package com.example.java_class_select.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "course_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseInfo {

	// 課程ID
	@Id
	@Column(name = "course_id")
	private String courseId;
	
	// 課程名稱
	@Column(name = "course_name")
	private String courseName;
	
	// 星期幾有課
	@Column(name = "day")
	private String day;
	
	// 幾點上課
	@Column(name = "start_time")
	private String courseStartTime;
	
	// 幾點下課
	@Column(name = "end_time")
	private String courseEndTime;
	
	// 多少學分
	@Column(name = "credits")
	private Integer credits;
	
	// 修課名單
	@Column(name = "persons")
	private String persons;

	

	
	public CourseInfo() {
		
	}

	public CourseInfo(String courseId, String courseName,  Integer credits) {
		this.courseId = courseId;
		this.courseName = courseName;
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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getCourseStartTime() {
		return courseStartTime;
	}

	public void setCourseStartTime(String courseStartTime) {
		this.courseStartTime = courseStartTime;
	}

	public String getCourseEndTime() {
		return courseEndTime;
	}

	public void setCourseEndTime(String courseEndTime) {
		this.courseEndTime = courseEndTime;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public String getPersons() {
		return persons;
	}

	public void setPersons(String persons) {
		this.persons = persons;
	}

	
	
	
	
}

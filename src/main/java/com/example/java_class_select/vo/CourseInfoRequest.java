package com.example.java_class_select.vo;

import java.util.List;

import javax.persistence.Column;

import com.example.java_class_select.entity.CourseInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseInfoRequest {

	@JsonProperty("courseinfo")
	private CourseInfo courseInfo;
	@JsonProperty("courseinfo_list")
	private List<CourseInfo> courseInfoList;
	
	@JsonProperty("courseid")
	private String courseId;
	
	@JsonProperty("coursename")
	private String courseName;
	
	@JsonProperty("credits")
	private Integer credits;

	public CourseInfoRequest() {

	}


	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	public List<CourseInfo> getCourseInfoList() {
		return courseInfoList;
	}

	public void setCourseInfoList(List<CourseInfo> courseInfoList) {
		this.courseInfoList = courseInfoList;
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


	public Integer getCredits() {
		return credits;
	}


	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	
}

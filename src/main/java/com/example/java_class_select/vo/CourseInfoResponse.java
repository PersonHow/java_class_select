package com.example.java_class_select.vo;

import java.util.List;

import com.example.java_class_select.entity.CourseInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseInfoResponse {

	@JsonProperty("courseinfo")
	private CourseInfo courseInfo;
	@JsonProperty("courseinfo_list")
	private List<CourseInfo> courseInfoList;
	
	private String message;

	public CourseInfoResponse() {
	}

	public CourseInfoResponse(List<CourseInfo> courseInfoList) {
		this.courseInfoList = courseInfoList;
	}
	
	public CourseInfoResponse(String message) {
		this.message = message;
	}
	
	public CourseInfoResponse(List<CourseInfo> courseInfoList,String message) {
		this.courseInfoList = courseInfoList;
		this.message = message;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

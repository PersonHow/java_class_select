package com.example.java_class_select.vo;

import java.util.List;

import com.example.java_class_select.entity.CourseInfo;
import com.example.java_class_select.entity.StudentInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentInfoRequest {

	@JsonProperty("studentinfo")
	private StudentInfo studentInfo;
	@JsonProperty("studentinfo_list")
	private List<StudentInfo> studentList;

	@JsonProperty("courseid_list")
	private List<String> courseIdList;
	
	@JsonProperty("courseid")
	private String courseId;
	@JsonProperty("studentid")
	private String studentId;
	
	@JsonProperty("type")
	private String type;
	
	public StudentInfoRequest() {

	}

	public StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

	public List<StudentInfo> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<StudentInfo> studentList) {
		this.studentList = studentList;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public List<String> getCourseIdList() {
		return courseIdList;
	}

	public void setCourseIdList(List<String> courseIdList) {
		this.courseIdList = courseIdList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	

	
}

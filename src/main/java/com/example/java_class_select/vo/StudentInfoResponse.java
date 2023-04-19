package com.example.java_class_select.vo;

import java.util.List;

import com.example.java_class_select.entity.CourseInfo;
import com.example.java_class_select.entity.StudentInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentInfoResponse {

	// 回傳學生相對應資料
	@JsonProperty("studentinfo")
	private StudentInfo studentInfo;
	
	//若有多筆學生資料時，以 List 方式呈現
	@JsonProperty("studentinfo_list")
	private List<StudentInfo> studentList;

	// 能回應課程 ID
	@JsonProperty("courseid")
	private String courseId;
	
	// 能回應學生 ID
	@JsonProperty("studentid")
	private String studentId;
	
	// 有多筆課程資料時，以List的方式呈現
	@JsonProperty("courseid_list")
	private List<CourseInfo> courseInfoList;
	private CourseInfo courseInfo;
	
	// 回傳訊息告知使用者結果
	private String message;
	
	// 回傳學生姓名
	private String studentname;
	
	// 回傳課程名稱
	private String courseName;
	


	public StudentInfoResponse() {
	}

	public StudentInfoResponse(String message) {
		this.message = message;
	}
	
	public StudentInfoResponse(StudentInfo studentInfo,String message) {
		this.studentInfo = studentInfo;
		this.message = message;
	}
	
	public StudentInfoResponse(StudentInfo studentInfo,CourseInfo courseInfo,String message) {
		this.studentInfo = studentInfo;
		this.courseInfo = courseInfo;
		this.message = message;
	}
	
	public StudentInfoResponse(StudentInfo studentInfo,String courseName,String message) {
		this.studentInfo = studentInfo;
		this.courseName = courseName;
		this.message = message;
	}
	
	public StudentInfoResponse(List<StudentInfo> studentList, String message) {
		this.studentList = studentList;
		this.message = message;
	}

	public StudentInfoResponse(String studentId, String studentname, CourseInfo courseInfo, String message) {
		this.studentId = studentId;
		this.studentname = studentname;
		this.courseInfo = courseInfo;
		this.message = message;

	}
	
	public StudentInfoResponse(String studentId, String studentname, List<CourseInfo> courseInfoList, String message) {
		this.studentId = studentId;
		this.studentname = studentname;
		this.courseInfoList = courseInfoList;
		this.message = message;

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

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public List<CourseInfo> getCourseInfoList() {
		return courseInfoList;
	}

	public void setCourseInfoList(List<CourseInfo> courseInfoList) {
		this.courseInfoList = courseInfoList;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	
}

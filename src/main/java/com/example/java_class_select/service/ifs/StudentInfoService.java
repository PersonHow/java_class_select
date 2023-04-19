package com.example.java_class_select.service.ifs;

import com.example.java_class_select.vo.StudentInfoRequest;
import com.example.java_class_select.vo.StudentInfoResponse;

public interface StudentInfoService {

	public StudentInfoResponse addStudent(StudentInfoRequest studentRequest);
	
	public StudentInfoResponse deleteStudent(StudentInfoRequest studentRequest);
	
	public StudentInfoResponse selectCourse(StudentInfoRequest studentRequest);
	
	public StudentInfoResponse serechStudentCourse(StudentInfoRequest studentRequest);
	
	public StudentInfoResponse joinCourse(StudentInfoRequest studentRequest);
	
	public StudentInfoResponse exitCourse(StudentInfoRequest studentRequest);
	

	
	
}

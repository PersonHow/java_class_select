package com.example.java_class_select.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_class_select.service.ifs.StudentInfoService;
import com.example.java_class_select.vo.StudentInfoRequest;
import com.example.java_class_select.vo.StudentInfoResponse;

@RestController
public class StudentInfoController {
	
	@Autowired
	private StudentInfoService studentInfoService;

	@RequestMapping(value = "add_student_Info" , method = RequestMethod.POST)
	public StudentInfoResponse addStudent(@RequestBody StudentInfoRequest studentRequest) {
		return studentInfoService.addStudent(studentRequest);
	}
	
	@RequestMapping(value = "delete_student_Info" , method = RequestMethod.POST)
	public StudentInfoResponse deleteStudent(@RequestBody StudentInfoRequest studentRequest) {
		return studentInfoService.deleteStudent(studentRequest);
	}
	
	@RequestMapping(value = "select_Course" , method = RequestMethod.POST)
	public StudentInfoResponse selectCourse(@RequestBody StudentInfoRequest studentRequest) {
		return studentInfoService.selectCourse(studentRequest);
	}
	
	@RequestMapping(value = "join_Course" , method = RequestMethod.POST)
	public StudentInfoResponse joinCourse(@RequestBody StudentInfoRequest studentRequest) {
		return studentInfoService.joinCourse(studentRequest);
	}
	
	@RequestMapping(value = "serech_Course" , method = RequestMethod.POST)
	public StudentInfoResponse serechStudentCourse(@RequestBody StudentInfoRequest studentRequest) {
		return studentInfoService.serechStudentCourse(studentRequest);
	}
	
	@RequestMapping(value = "exit_Course" , method = RequestMethod.POST)
	public StudentInfoResponse exitCourse(@RequestBody StudentInfoRequest studentRequest) {
		return studentInfoService.exitCourse(studentRequest);
	}

	
	
}



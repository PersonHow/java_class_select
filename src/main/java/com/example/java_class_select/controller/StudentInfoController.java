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

	// 連接 Postman 與實作的新增學生資料
	@RequestMapping(value = "add_student_Info" , method = RequestMethod.POST)
	public StudentInfoResponse addStudent(@RequestBody StudentInfoRequest studentRequest) {
		return studentInfoService.addStudent(studentRequest);
	}
	
	// 連接 Postman 與實作的刪除學生資料
	@RequestMapping(value = "delete_student_Info" , method = RequestMethod.POST)
	public StudentInfoResponse deleteStudent(@RequestBody StudentInfoRequest studentRequest) {
		return studentInfoService.deleteStudent(studentRequest);
	}
	
	// 連接 Postman 與實作的選課
	@RequestMapping(value = "select_Course" , method = RequestMethod.POST)
	public StudentInfoResponse selectCourse(@RequestBody StudentInfoRequest studentRequest) {
		return studentInfoService.selectCourse(studentRequest);
	}
	
	// 連接 Postman 與實作的搜尋學生已選課資訊
	@RequestMapping(value = "serech_Course" , method = RequestMethod.POST)
	public StudentInfoResponse serechStudentCourse(@RequestBody StudentInfoRequest studentRequest) {
		return studentInfoService.serechStudentCourse(studentRequest);
	}
	
	// 連接 Postman 與實作的加退選
	@RequestMapping(value = "course_Exit_Or_Join" , method = RequestMethod.POST)
	public StudentInfoResponse courseExitOrJoin(@RequestBody StudentInfoRequest studentRequest) {
		return studentInfoService.courseExitOrJoin(studentRequest);
	}

	
	
}



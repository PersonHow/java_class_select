package com.example.java_class_select.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_class_select.entity.CourseInfo;
import com.example.java_class_select.service.ifs.CourseInfoService;
import com.example.java_class_select.vo.CourseInfoRequest;
import com.example.java_class_select.vo.CourseInfoResponse;

@CrossOrigin
@RestController
public class CourseInfoController {

	@Autowired
	private CourseInfoService courseService;
	
	// 接受 Postman 以及連接實作的新建課程資料功能
	@RequestMapping(value = "create_Course" , method = RequestMethod.POST)
	public CourseInfoResponse createCourse (@RequestBody CourseInfoRequest courseInfoRequest) {
		return courseService.createCourse(courseInfoRequest);
	}
	
	// 接受 Postman 以及連接實作的刪除課程資料功能
	@PostMapping("delete_Course")
	public CourseInfoResponse deleteCourse (@RequestBody CourseInfoRequest courseInfoRequest) {
		return courseService.deleteCourse(courseInfoRequest);
	}
	
	// 接受 Postman 以及連接實作的使用課程ID搜尋課程功能
	@PostMapping("course_Serech_ById")
	public List<CourseInfo>  courseSerechById(@RequestBody CourseInfoRequest request){
		return courseService.courseSerechById(request.getMessage());
	}
	
	// 接受 Postman 以及連接實作的使用課程名稱搜尋課程功能
	@PostMapping("course_Serech_ByName")
	public List<CourseInfo>  courseSerechByName(@RequestBody CourseInfoRequest request){
		return courseService.courseSerechByName(request.getMessage());
	}
	
	// 接受 Postman 以及連接實作的修改課程資料功能
	@PostMapping("edit_Credits")
	public CourseInfoResponse editCredits(@RequestBody CourseInfoRequest request) {
		return courseService.editCredits(request, request.getCourseId());
	}
	
	@PostMapping("show_All_Course")
	public List<CourseInfo> showAllCourse(@RequestBody CourseInfoRequest request){
		return courseService.showAllCourse(request.getMessage());
	}
}

package com.example.java_class_select.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_class_select.entity.CourseInfo;
import com.example.java_class_select.service.ifs.CourseInfoService;
import com.example.java_class_select.vo.CourseInfoRequest;
import com.example.java_class_select.vo.CourseInfoResponse;

@RestController
public class CourseInfoController {

	@Autowired
	private CourseInfoService courseService;
	
	@RequestMapping(value = "create_Course" , method = RequestMethod.POST)
	public CourseInfoResponse createCourse (@RequestBody CourseInfoRequest courseInfoRequest) {
		return courseService.createCourse(courseInfoRequest);
	}
	
	@PostMapping("delete_Course")
	public CourseInfoResponse deleteCourse (@RequestBody CourseInfoRequest courseInfoRequest) {
		return courseService.deleteCourse(courseInfoRequest);
	}
	
	@PostMapping("course_Serech_ById")
	public List<CourseInfo>  courseSerechById(@RequestBody CourseInfoRequest request){
		return courseService.courseSerechById(request.getCourseId());
	}
	
	@PostMapping("course_Serech_ByName")
	public List<CourseInfo>  courseSerechByName(@RequestBody CourseInfoRequest request){
		return courseService.courseSerechByName(request.getCourseName());
	}
	
	@PostMapping("edit_Credits")
	public CourseInfoResponse editCredits(@RequestBody CourseInfoRequest request) {
		return courseService.editCredits(request.getCourseId(), request.getCredits());
	}
}

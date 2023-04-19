package com.example.java_class_select.service.ifs;

import java.util.List;

import com.example.java_class_select.entity.CourseInfo;
import com.example.java_class_select.vo.CourseInfoRequest;
import com.example.java_class_select.vo.CourseInfoResponse;

public interface CourseInfoService {

	public CourseInfoResponse createCourse (CourseInfoRequest courseInfoRequest); 
	
	public CourseInfoResponse deleteCourse (CourseInfoRequest courseInfoRequest); 
	
	public List<CourseInfo>  courseSerechById(String id);
	
	public List<CourseInfo>  courseSerechByName(String name);
	
	public CourseInfoResponse editCredits(String id,int credits);
}

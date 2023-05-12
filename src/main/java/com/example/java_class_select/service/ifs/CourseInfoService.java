package com.example.java_class_select.service.ifs;

import java.util.List;

import com.example.java_class_select.entity.CourseInfo;
import com.example.java_class_select.vo.CourseInfoRequest;
import com.example.java_class_select.vo.CourseInfoResponse;

public interface CourseInfoService {

	// 新建課程資料
	public CourseInfoResponse createCourse (CourseInfoRequest courseInfoRequest); 
	
	// 刪除課程資料
	public CourseInfoResponse deleteCourse (CourseInfoRequest courseInfoRequest); 
	
	// 使用課程ID搜尋課程
	public List<CourseInfo>  courseSerechById(String id);
	
	// 使用課程名稱搜尋課程
	public List<CourseInfo>  courseSerechByName(String name);
	
	// 課程資料的修改
	public CourseInfoResponse editCredits(CourseInfoRequest courseInfoRequest, String id);
	
	public List<CourseInfo> showAllCourse(String text);
}

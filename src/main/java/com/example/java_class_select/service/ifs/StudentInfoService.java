package com.example.java_class_select.service.ifs;

import com.example.java_class_select.vo.StudentInfoRequest;
import com.example.java_class_select.vo.StudentInfoResponse;

public interface StudentInfoService {

	//新增學生資料
	public StudentInfoResponse addStudent(StudentInfoRequest studentRequest);
	
	//刪除學生資料
	public StudentInfoResponse deleteStudent(StudentInfoRequest studentRequest);
	
	// 選課
	public StudentInfoResponse selectCourse(StudentInfoRequest studentRequest);
	
	// 搜尋學生已選課資訊
	public StudentInfoResponse serechStudentCourse(StudentInfoRequest studentRequest);

	// 加退選
	public StudentInfoResponse courseExitOrJoin(StudentInfoRequest studentRequest);
	

	
	
}

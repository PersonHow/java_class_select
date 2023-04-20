package com.example.java_class_select.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.java_class_select.entity.CourseInfo;
import com.example.java_class_select.repository.CourseInfoDao;
import com.example.java_class_select.service.ifs.CourseInfoService;
import com.example.java_class_select.vo.CourseInfoRequest;
import com.example.java_class_select.vo.CourseInfoResponse;

@Service
public class CourseInfoServiceImpl implements CourseInfoService {

	@Autowired
	private CourseInfoDao courseInfoDao;

	
//=================================================================================================	
	@Override
	public CourseInfoResponse createCourse(CourseInfoRequest courseInfoRequest) {
		// 預新增的課程資料，可新增多種課程
		List<CourseInfo> courseList = courseInfoRequest.getCourseInfoList();
		// 逐一取出判斷
		for (CourseInfo item : courseList) {
			// 判斷預新增課程是否已開課
			if (courseInfoDao.existsById(item.getCourseId())) {
				return new CourseInfoResponse("已開課");
			}
			// ID與課程名稱不能為空
			if (!StringUtils.hasText(item.getCourseId()) || !StringUtils.hasText(item.getCourseName())) {
				return new CourseInfoResponse("格式錯誤");
			}
			// 課程學分要填
			if (!StringUtils.hasText(item.getCredits().toString())) {
				return new CourseInfoResponse("要填幾學分");
			}
			// 上課是星期幾以及幾點上下課要填
			if (!StringUtils.hasText(item.getDay()) || !StringUtils.hasText(item.getCourseStartTime())
					|| !StringUtils.hasText(item.getCourseEndTime())) {
				return new CourseInfoResponse("開課時間要填");
			}
		}
		courseInfoDao.saveAll(courseList);
		return new CourseInfoResponse(courseList, "開課完成");
	}
	
//=================================================================================================
	@Override
	public CourseInfoResponse deleteCourse(CourseInfoRequest courseInfoRequest) {
		// 找到預刪除的課程ID
		String id = courseInfoRequest.getCourseId();
		
		// DB要有該課程資料才動作
		if (courseInfoDao.existsById(id)) {
			Optional<CourseInfo> deleteOp = courseInfoDao.findById(id);
			CourseInfo delete = deleteOp.get();
			
			// 若還有人選課則不能刪除
			if(StringUtils.hasText(delete.getPersons()) ) {
				return  new CourseInfoResponse("還有人要修，白癡!!!");
			}
			courseInfoDao.deleteById(id);
			return  new CourseInfoResponse("刪除課程成功");
		}
		return new CourseInfoResponse("沒找到開課資訊");
	}

//=================================================================================================
	@Override
	public List<CourseInfo> courseSerechById(String id) {
		Optional <CourseInfo> op = courseInfoDao.findById(id);
		return Arrays.asList(op.get());
	}

//=================================================================================================
	@Override
	public List<CourseInfo> courseSerechByName(String name) {
		return courseInfoDao.findBycourseName(name);
	}

//=================================================================================================
	@Override
	public CourseInfoResponse editCredits(CourseInfoRequest courseInfoRequest, String id) {
		// 預變更的資料
		CourseInfo changeCourse = courseInfoRequest.getCourseInfo();
		if(!StringUtils.hasText(id) ) {
			return new CourseInfoResponse("要填ID");
		}
		if(!courseInfoDao.existsById(id)) {
			return new CourseInfoResponse("沒有該課程");
		}
		// 要被修改的課程資料
		Optional <CourseInfo> op = courseInfoDao.findById(id);
		CourseInfo cuInfo = op.get();
		// 預修正的資料
		// 若有填預修改的相關資料，便更動
		if(!StringUtils.hasText(changeCourse.getCourseId())) {
			cuInfo.setCourseId(changeCourse.getCourseId());
		}
		
		if(!StringUtils.hasText(changeCourse.getCourseName())) {
			cuInfo.setCourseName(changeCourse.getCourseName());
		}
		
		if(!StringUtils.hasText(changeCourse.getCourseStartTime())) {
			cuInfo.setCourseStartTime(changeCourse.getCourseStartTime());
		}
		
		if(!StringUtils.hasText(changeCourse.getCourseEndTime())) {
			cuInfo.setCourseEndTime(changeCourse.getCourseEndTime());
		}
		
		if(!StringUtils.hasText(changeCourse.getDay())) {
			cuInfo.setDay(changeCourse.getDay());
		}
		
		if(!StringUtils.hasText(changeCourse.getCredits().toString())) {
			cuInfo.setCredits(changeCourse.getCredits());
		}
		// 都沒問題就儲存
		courseInfoDao.save(cuInfo);
		return new CourseInfoResponse("課程資料修改成功");
	}
}

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
		List<CourseInfo> courseList = courseInfoRequest.getCourseInfoList();
		for (CourseInfo item : courseList) {
			if (courseInfoDao.existsById(item.getCourseId())) {
				return new CourseInfoResponse("已開課");
			}
			if (!StringUtils.hasText(item.getCourseId()) || !StringUtils.hasText(item.getCourseName())) {
				return new CourseInfoResponse("格式錯誤");
			}
			if (!StringUtils.hasText(item.getCredits().toString())) {
				return new CourseInfoResponse("要填幾學分");
			}
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
		String id = courseInfoRequest.getCourseId();
		if (courseInfoDao.existsById(id)) {
			Optional<CourseInfo> deleteOp = courseInfoDao.findById(id);
			CourseInfo delete = deleteOp.get();
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
	public CourseInfoResponse editCredits(String id,int credits) {
		Optional <CourseInfo> op = courseInfoDao.findById(id);
		CourseInfo cuInfo = op.get();
		cuInfo.setCredits(credits);
		courseInfoDao.save(cuInfo);
		return new CourseInfoResponse("學分修改成功");
	}
}

package com.example.java_class_select.service.impl;

import java.util.ArrayList;
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
		List<String> dayList = new ArrayList<>();
		dayList.add("星期一");
		dayList.add("星期二");
		dayList.add("星期三");
		dayList.add("星期四");
		dayList.add("星期五");
		
		// 逐一取出判斷
		for (CourseInfo item : courseList) {
			String cid = item.getCourseId();
			String cname = item.getCourseName();
			String idPattern = "\\w{3,5}";
			String namePattern = "\\S{3,10}";
			
			// 判斷預新增課程是否已開課
			if (courseInfoDao.existsById(item.getCourseId())) {
				return new CourseInfoResponse("已開課");
			}
			// ID與課程名稱不能為空
			if (!StringUtils.hasText(item.getCourseId()) || !StringUtils.hasText(item.getCourseName()) 
					|| cid.equalsIgnoreCase("null") || cname.equalsIgnoreCase("null")){			
					return new CourseInfoResponse("不能為null");				
			}
		
			if(!cid.matches(idPattern) || !cname.matches(namePattern)) {
				return new CourseInfoResponse("格式錯誤");
			}
	
			
			// 課程學分要填
			if (item.getCredits() > 3 || item.getCredits() <= 0) {
				return new CourseInfoResponse("你在開我玩笑嗎?");
			}
			
			
			// 上課是星期幾以及幾點上下課要填
			String timePattern = "\\d{4}";
			
			if (!StringUtils.hasText(item.getDay()) || !StringUtils.hasText(item.getCourseStartTime()) 
					|| !StringUtils.hasText(item.getCourseEndTime())){
				return new CourseInfoResponse("開課時間要填");
			}
			
			if (Integer.parseInt(item.getCourseStartTime()) < 800 || Integer.parseInt(item.getCourseEndTime()) > 1700 
					|| Integer.parseInt(item.getCourseStartTime()) == 1200 
					|| !item.getCourseStartTime().matches(timePattern)
					|| !item.getCourseEndTime().matches(timePattern)) {
				return new CourseInfoResponse("開課時間錯誤");
			}
			
			if(!dayList.contains(item.getDay())) {
				return new CourseInfoResponse("開課時間要正確");
			}
	
		}
//		List<CourseInfo> courseList = courseInfoRequest.getCourseInfoList();
//		CI(courseList);
//		courseInfoDao.saveAll(courseList);
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
				return new CourseInfoResponse("還有人要修，白癡!!!");
			}
			courseInfoDao.deleteById(id);
			return new CourseInfoResponse("刪除課程成功");
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
	public List<CourseInfo> showAllCourse(String text){
		System.out.println(text);
		if(text.equalsIgnoreCase("all")) {
			return courseInfoDao.findAll();
		}
		List<CourseInfo> list = new ArrayList<CourseInfo>();
		CourseInfo t = new CourseInfo();
		System.out.println(text);
		t.setCourseName("請輸入正確關鍵字 All");
		list.add(t);
		return list;
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
		
		String idPattern = "\\w{3,6}";
		String namePattern = "\\S{2,10}";
		
		// 要被修改的課程資料
		Optional <CourseInfo> op = courseInfoDao.findById(id);
		CourseInfo cuInfo = op.get();
		// 預修正的資料
		// 若有填預修改的相關資料，便更動
		if (StringUtils.hasText(changeCourse.getCourseId())) {
			if (changeCourse.getCourseId().matches(idPattern)) {
				cuInfo.setCourseId(changeCourse.getCourseId());
			}
		}

		if (StringUtils.hasText(changeCourse.getCourseName())) {
			if (changeCourse.getCourseName().matches(namePattern)) {
				cuInfo.setCourseName(changeCourse.getCourseName());
			}
		}
		
		
		
		String timePattern = "\\d{4}";
		if (StringUtils.hasText(changeCourse.getCourseStartTime()) 
				&& StringUtils.hasText(changeCourse.getCourseEndTime())) {
			int startTime = Integer.parseInt(changeCourse.getCourseStartTime());
			int endTime = Integer.parseInt(changeCourse.getCourseEndTime());
			
			if(startTime > endTime) {
				return new CourseInfoResponse("這個時間確定餒");
			}
			if (changeCourse.getCourseStartTime().matches(timePattern) 
					&& changeCourse.getCourseEndTime().matches(timePattern)) {
				cuInfo.setCourseStartTime(changeCourse.getCourseStartTime());
				cuInfo.setCourseEndTime(changeCourse.getCourseEndTime());
			} else {
				return new CourseInfoResponse("超出開課時間");
			}
		}

		List<String> dayList = new ArrayList<>();
		dayList.add("星期一");
		dayList.add("星期二");
		dayList.add("星期三");
		dayList.add("星期四");
		dayList.add("星期五");
		
		if (StringUtils.hasText(changeCourse.getDay())) {
			if (dayList.contains(changeCourse.getDay())) {
				cuInfo.setDay(changeCourse.getDay());
			} else {
				return new CourseInfoResponse("超出開課時間");
			}
		}
		
		if (changeCourse.getCredits() != null) {
			if (changeCourse.getCredits() <= 3 && changeCourse.getCredits() > 0) {
				cuInfo.setCredits(changeCourse.getCredits());
			} else {
				return new CourseInfoResponse("學分要克制");
			}
		}
		
		
			
		
		// 都沒問題就儲存
//		courseInfoDao.save(cuInfo);
		return new CourseInfoResponse(cuInfo,"課程資料修改成功");
	}
	
	
//============================================================================
	private List<CourseInfo> CI(List<CourseInfo> cList){
		List<CourseInfo> CIList = cList;
		return null;
	}
//	
//	
}

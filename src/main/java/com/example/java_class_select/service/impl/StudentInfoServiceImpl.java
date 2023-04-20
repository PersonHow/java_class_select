package com.example.java_class_select.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.java_class_select.entity.CourseInfo;
import com.example.java_class_select.entity.StudentInfo;
import com.example.java_class_select.repository.CourseInfoDao;
import com.example.java_class_select.repository.StudentInfoDao;
import com.example.java_class_select.service.ifs.StudentInfoService;
import com.example.java_class_select.vo.StudentInfoRequest;
import com.example.java_class_select.vo.StudentInfoResponse;

@Service
public class StudentInfoServiceImpl implements StudentInfoService {

	@Autowired
	private StudentInfoDao studentInfoDao;

	@Autowired
	private CourseInfoDao courseInfoDao;

//=================================================================================================	
	// 新增或更新學生資料 歐虧
	@Override
	public StudentInfoResponse addStudent(StudentInfoRequest studentRequest) {
		List<StudentInfo> infoList = studentRequest.getStudentList();

		// 先將輸入的資料逐一取出
		for (StudentInfo item : infoList) {
			// 判斷學生ID 或學生姓名是否沒填
			if (!StringUtils.hasText(item.getStudentId()) || !StringUtils.hasText(item.getName())) {
				return new StudentInfoResponse("格式錯誤");
			}
		}
		studentInfoDao.saveAll(infoList);
		return new StudentInfoResponse(infoList, "學生資料更新成功");

	}

//=================================================================================================
	// 刪除學生資料 歐虧
	@Override
	public StudentInfoResponse deleteStudent(StudentInfoRequest studentRequest) {
		List<StudentInfo> stInfoList = studentRequest.getStudentList();
		if (!stInfoList.isEmpty()) {
			for (int i = 0; i < stInfoList.size(); i++) {
				StudentInfo stInfo = stInfoList.get(i);
				String stCourseId = stInfo.getCourseId();

				// 判斷是否有課尚未退選
				if (StringUtils.hasText(stCourseId)) {
					return new StudentInfoResponse("尚有課程未退選");
				}
				studentInfoDao.deleteById(stInfo.getStudentId());
			}
			return new StudentInfoResponse("刪除資料成功");
		}
		return new StudentInfoResponse("請輸入學生ID");
	}

//=================================================================================================
	// 選課
	@Override
	public StudentInfoResponse selectCourse(StudentInfoRequest studentRequest) {
		// 輸入的選課資訊，型態是 List
		List<String> cuIdList = studentRequest.getCourseIdList();

		// 針對輸入的學生ID，並從相對應 DB 中撈資料
		StudentInfo stInfo = getStudentInfo(studentRequest);

		// 建立能存放學生選課ID相對應名稱的 List
		List<String> stCourseNameList = new ArrayList<>();

		// 取得該學生目前的選課資訊
		String stInfoCourseId = stInfo.getCourseId();

		// 判斷是否已選課，若已有課程則應改用加選系統
		if (!StringUtils.hasText(stInfoCourseId)) {
			// 先從第一個課程開始與往後每一個課程兩兩比較，驗證相關資訊是否衝突
			// 逐一取出輸入的選課資訊
			for (int i = 0; i < cuIdList.size(); i++) {
				// cuIdList = 輸入選課的 List
				// cuInfo 是被比較值，用來判斷課程ID、課程名稱、上課時間是否相衝
				CourseInfo cuInfo = courseInfoDao.findById(cuIdList.get(i)).get();
				String item = cuIdList.get(i);

				for (int j = i + 1; j < cuIdList.size(); j++) {
					// cuInfoTime : 拿來與 cuInfo 比較
					CourseInfo cuInfoTime = courseInfoDao.findById(cuIdList.get(j)).get();
					String cuInfoTimeItem = cuIdList.get(j);

					// 檢查是否包含相同課程 ID
					if (item.equals(cuInfoTimeItem)) {
						return new StudentInfoResponse("已選擇該課程");
					}

					// 檢查是否包含相同課程名稱
					if (cuInfo.getCourseName().equals(cuInfoTime.getCourseName())) {
						return new StudentInfoResponse("已選課中包含相同課程名稱");
					}

					// 檢查是否衝堂
					// 該DB的上下課時間都是 String，所以先轉數字
					// cuInfo是被比較值，也就是第一個選的課程
					int cuInfoEndTime = Integer.parseInt(cuInfo.getCourseEndTime());
					int cuInfoStartTime = Integer.parseInt(cuInfo.getCourseStartTime());
					// 比較值
					int cuInfoTimeStart = Integer.parseInt(cuInfoTime.getCourseStartTime());
					int cuInfoTimeEnd = Integer.parseInt(cuInfo.getCourseEndTime());

					// 因為最早的課是0800開始，最多上三個小時
					// 結束時間是1100，故 0800~1100 期間不得選其他課否則會相
					// 小於1000的時間會變成 900、800
					// 因為已轉整數，故使用減法相互比較
					
					// 先比較星期
					if (cuInfo.getDay().equals(cuInfoTime.getDay())) {
						// 再比較上課時間
						if (cuInfoStartTime == cuInfoTimeStart || cuInfoEndTime == cuInfoTimeStart
								|| cuInfoEndTime == cuInfoTimeEnd 
								|| (cuInfoEndTime - 100) == cuInfoTimeStart
								|| (cuInfoEndTime - 200) == cuInfoTimeStart
								|| (cuInfoEndTime - 300) == cuInfoTimeStart) {
							return new StudentInfoResponse("上課時間相衝");
						}
					}
				}

				// 檢查已選課程選修人數是否已滿
				List<String> cuPersonsList = new ArrayList<>();
				// 若該課堂目前都沒人選，則直接加入
				if (!StringUtils.hasText(cuInfo.getPersons())) {
					cuInfo.setPersons(stInfo.getStudentId());
					
				} else {
					// 若有人選課則逐一取出來供判斷是否超出上限
					for (String item2 : cuInfo.getPersons().split(",")) {

						cuPersonsList.add(item2);

						// 每門課修課人數不能超過三人
						if (cuPersonsList.size() >= 3) {
							return new StudentInfoResponse("該課程人數已滿");
						}
					}
					// 沒超出上限則加回List，再使用 String.join 逐一變回字串
					cuPersonsList.add(stInfo.getStudentId());
					String cuJoinPersons = String.join(",", cuPersonsList);
					cuInfo.setPersons(cuJoinPersons);
					
				}

				// 判斷學生目前的學分，確認可以選課才對學分進行更動
				// 用以計算課程學分
				int totalCredits = 0;

				// 先取得目前學生的學分
				totalCredits = stInfo.getCredits();

				// 學生目前的學分加上該選課的學分
				totalCredits += cuInfo.getCredits();
				// 加總學分不能超過10
				if (totalCredits > 10) {
					return new StudentInfoResponse("已超出學分上限");
				}
				// 總學分沒超過 10 則調整學生的學分
				stInfo.setCredits(totalCredits);
				
				// 學分也沒問題才儲存課堂人數資料
				courseInfoDao.save(cuInfo);
			}
			// 全部比較完沒問題則確認可以選課
			stInfo.setCourseId(String.join(",", cuIdList));

			// 逐一根據 ID 取得名字並加入字串
			for (String names : cuIdList) {
				stCourseNameList.add(courseInfoDao.findById(names).get().getCourseName());
			}
			stInfo.setCourseName(String.join(",", stCourseNameList));
			studentInfoDao.save(stInfo);

			return new StudentInfoResponse(stInfo, "選課成功");
		}
		// 若已有課應該使用加選系統
		return new StudentInfoResponse("應該使用加選系統");
	}

//=================================================================================================
	// 學生已選課程查詢 歐虧
	@Override
	public StudentInfoResponse serechStudentCourse(StudentInfoRequest studentRequest) {
		// 先建立能存放課程型態的 List
		List<CourseInfo> cuInfoList = new ArrayList<>();
		String id = studentRequest.getStudentId();

		// 防呆，怕手殘直接輸入空白
		if (!StringUtils.hasText(id)) {
			return new StudentInfoResponse("格式錯誤");
		}
		// 先由 ID 找到學生的相關資訊，有找到才允許查詢
		if (studentInfoDao.existsById(id)) {
			Optional<StudentInfo> stOp = studentInfoDao.findById(id);
			StudentInfo stget = stOp.get();
			// 這邊需要調整
			for (String item : stget.getCourseId().split(",")) {
				Optional<CourseInfo> cuOp = courseInfoDao.findById(item);
				CourseInfo cuInfo = cuOp.get();
				cuInfoList.add(cuInfo);
			}
			return new StudentInfoResponse(stget.getStudentId(), stget.getName(), cuInfoList, "查詢成功");
		}
		return new StudentInfoResponse("無相關資料");
	}

//=================================================================================================
	// 加退選課程 歐虧
	@Override
	public StudentInfoResponse courseExitOrJoin(StudentInfoRequest studentRequest) {
		String type = studentRequest.getType();
		List<String> typeList = new ArrayList<>();
		typeList.add("join");
		typeList.add("exit");
		
		// 必須包含加退選的關鍵字
		if (typeList.contains(type)) {
			// 退選功能
			if (type.equals("exit")) {
				StudentInfo stInfo = getStudentInfo(studentRequest);
				CourseInfo cuInfo = getCourseInfo(studentRequest.getCourseId());

				// 防呆，避免任何一個 ID 是空值
				if (!StringUtils.hasText(stInfo.getStudentId()) || !StringUtils.hasText(cuInfo.getCourseId())) {
					return new StudentInfoResponse("ID格式錯誤");
				}

				// 把學生已選課的資訊取出
				String stInfoCourseId = stInfo.getCourseId();
				List<String> stCourseIdList = new ArrayList<>();
				List<String> stCourseNameList = new ArrayList<>();
				// 用","區分並逐一放入 List
				for (String item : stInfoCourseId.split(",")) {
					Optional<CourseInfo> cuName = courseInfoDao.findById(item);
					CourseInfo cuNameGet = cuName.get();
					stCourseIdList.add(item);
					stCourseNameList.add(cuNameGet.getCourseName());
				}

				// 防呆，檢查欲退選的課是否已選上
				if (!stCourseIdList.contains(cuInfo.getCourseId())) {
					return new StudentInfoResponse("並無選擇該課程");
				}

				// 課程ID 與名稱分別放入對應的 List 後執行退選，再使用 join 方式變回字串
				stCourseIdList.remove(cuInfo.getCourseId());
				stCourseNameList.remove(cuInfo.getCourseName());

				// 退選後學分也要跟著變動
				int totalCredits = 0;
				totalCredits = (stInfo.getCredits() - cuInfo.getCredits());

				String stJoinCourseId = String.join(",", stCourseIdList);
				String stJoinCourseName = String.join(",", stCourseNameList);
				stInfo.setCourseId(stJoinCourseId);
				stInfo.setCourseName(stJoinCourseName);
				stInfo.setCredits(totalCredits);
				studentInfoDao.save(stInfo);

				List<String> cuInfoPersons = new ArrayList<>();
				for (String item : cuInfo.getPersons().split(",")) {
					if (!item.equals(stInfo.getStudentId())) {
						cuInfoPersons.add(item);
					}
				}
				cuInfo.setPersons(String.join(",", cuInfoPersons));
				courseInfoDao.save(cuInfo);
				return new StudentInfoResponse(stInfo, "退選成功");
			} // 退選的尾巴

			// 加選
			if (type.equals("join")) {
				StudentInfo stInfo = getStudentInfo(studentRequest);
				String stInfoCourseId = stInfo.getCourseId();
				List<String> cuIdList = studentRequest.getCourseIdList();
				// DB取出來的ID是"String"，建立一個可以存放的List
				List<String> stCourseIdList = new ArrayList<>();
				List<String> stCourseNameList = new ArrayList<>();
				// 判斷選課時是否已選該課程或是已具有相同課程名稱
				for (String item : stInfoCourseId.split(",")) {
					// 取得目前學生已選課ID的相對應名稱

					CourseInfo cuNameGet = getCourseInfo(item);

					// 若已選課，則需要比對是否有重複選到相同課程
					for (int i = 0; i < cuIdList.size(); i++) {
						CourseInfo cuInfo = getCourseInfo(cuIdList.get(i));

						if (item.equals(cuIdList.get(i))) {
							return new StudentInfoResponse("已選擇該課程");
						}

						// 檢查已選課程中是否包含相同課程名稱
						if (cuNameGet.getCourseName().equals(cuInfo.getCourseName())) {
							return new StudentInfoResponse("已選課中包含相同課程名稱");
						}

						// 檢查選課時間是否與已選課相衝
						int stEndTime = Integer.parseInt(cuNameGet.getCourseEndTime());
						int stStartTime = Integer.parseInt(cuNameGet.getCourseStartTime());
						int cuInfoEndTime = Integer.parseInt(cuInfo.getCourseEndTime());
						int cuInfoStartTime = Integer.parseInt(cuInfo.getCourseStartTime());
					
						if (cuInfo.getDay().equals(cuNameGet.getDay())) {
							if(cuInfoStartTime == stStartTime || cuInfoEndTime == stStartTime 
									||cuInfoStartTime == stEndTime ||cuInfoStartTime == (stEndTime-100)
									||cuInfoStartTime == (stEndTime-200) || cuInfoStartTime == (stEndTime-300)) {
								return new StudentInfoResponse("上課時間相衝");
							}
						}

						// for 迴圈的尾巴
					}
					stCourseIdList.add(item);
					stCourseNameList.add(cuNameGet.getCourseName());
					// foreach 迴圈的尾巴
				}
				for (String names : cuIdList) {	
					CourseInfo cuInfo = getCourseInfo(names);
					// 檢查已選課程選修人數是否已滿
					List<String> cuPersonsList = new ArrayList<>();
					if (!StringUtils.hasText(cuInfo.getPersons())) {
						cuInfo.setPersons(stInfo.getStudentId());
//						courseInfoDao.save(cuInfo);
					} else {
						for (String item2 : cuInfo.getPersons().split(",")) {

							cuPersonsList.add(item2);

							// 每門課修課人數不能超過三人
							if (cuPersonsList.size() > 3) {
								return new StudentInfoResponse("該課程人數已滿");
							}
						}
						cuPersonsList.add(stInfo.getStudentId());
						String cuJoinPersons = String.join(",", cuPersonsList);
						cuInfo.setPersons(cuJoinPersons);
						
					}

					// 判斷學生目前的學分，確認可以選課學分才增加
					// 先取得目前學生的學分
					int totalCredits = 0;
					totalCredits = stInfo.getCredits();

					// 學生目前的學分加上該選課的學分
					totalCredits += cuInfo.getCredits();
					// 總學分不能超過10
					if (totalCredits > 10) {
						return new StudentInfoResponse("已超出學分上限");
					}
					// 總學分沒超過 10 則調整學生的學分
					stInfo.setCredits(totalCredits);

					stCourseIdList.add(names);
					stCourseNameList.add(courseInfoDao.findById(names).get().getCourseName());
					courseInfoDao.save(cuInfo);
				}
				
				stInfo.setCourseId(String.join(",", stCourseIdList));
				stInfo.setCourseName(String.join(",", stCourseNameList));
				studentInfoDao.save(stInfo);
				return new StudentInfoResponse(stInfo, "選課成功");
			} // 加選的尾巴
		}// 判斷是否包含關鍵字的尾巴
		return new StudentInfoResponse("要加選 (join) 還是退選 (exit) ?");
	}

//=================================================================================================
//	// 加選 歐虧
//	@Override
//	public StudentInfoResponse joinCourse(StudentInfoRequest studentRequest) {
//		StudentInfo stInfo = getStudentInfo(studentRequest);
//		String stInfoCourseId = stInfo.getCourseId();
//		List<String> cuIdList = studentRequest.getCourseIdList();
//		// DB取出來的ID是"String"，建立一個可以存放的List
//		List<String> stCourseIdList = new ArrayList<>();
//		List<String> stCourseNameList = new ArrayList<>();
//		// 判斷選課時是否已選該課程或是已具有相同課程名稱
//		for (String item : stInfoCourseId.split(",")) {
//			// 取得目前學生已選課ID的相對應名稱
//
//			CourseInfo cuNameGet = getCourseInfo(item);
//
//			// 若已選課，則需要比對是否有重複選到相同課程
//			for (int i = 0; i < cuIdList.size(); i++) {
//				CourseInfo cuInfo = getCourseInfo(cuIdList.get(i));
//
//				if (item.equals(cuIdList.get(i))) {
//					return new StudentInfoResponse("已選擇該課程");
//				}
//
//				// 檢查已選課程中是否包含相同課程名稱
//
//				if (cuNameGet.getCourseName().equals(cuInfo.getCourseName())) {
//					return new StudentInfoResponse("已選課中包含相同課程名稱");
//				}
//
//				// 檢查選課時間是否與已選課相衝
//				int stEndTime = Integer.parseInt(cuNameGet.getCourseEndTime());
//				String time1, time2, time3;
//				if (stEndTime > 1000) {
//					time1 = "0" + Integer.toString((stEndTime - 100));
//					time2 = "0" + Integer.toString((stEndTime - 200));
//					time3 = "0" + Integer.toString((stEndTime - 300));
//				} else {
//					time1 = Integer.toString((stEndTime - 100));
//					time2 = Integer.toString((stEndTime - 200));
//					time3 = Integer.toString((stEndTime - 300));
//				}
//				if (cuInfo.getDay().equals(cuNameGet.getDay())) {
//					if (cuInfo.getCourseStartTime().equals(cuNameGet.getCourseStartTime())
//							|| cuInfo.getCourseEndTime().equals(cuNameGet.getCourseStartTime())
//							|| cuInfo.getCourseStartTime().equals(time1) || cuInfo.getCourseStartTime().equals(time2)
//							|| cuInfo.getCourseStartTime().equals(time3)) {
//						return new StudentInfoResponse("上課時間相衝");
//					}
//				}
//
//				// for 迴圈的尾巴
//			}
//			stCourseIdList.add(item);
//			stCourseNameList.add(cuNameGet.getCourseName());
//			// foreach 迴圈的尾巴
//		}
//		for (String names : cuIdList) {
//			CourseInfo cuInfo = getCourseInfo(names);
//			// 檢查已選課程選修人數是否已滿
//			List<String> cuPersonsList = new ArrayList<>();
//			if (!StringUtils.hasText(cuInfo.getPersons())) {
//				cuInfo.setPersons(stInfo.getStudentId());
//				courseInfoDao.save(cuInfo);
//			} else {
//				for (String item2 : cuInfo.getPersons().split(",")) {
//
//					cuPersonsList.add(item2);
//
//					// 每門課修課人數不能超過三人
//					if (cuPersonsList.size() > 3) {
//						return new StudentInfoResponse("該課程人數已滿");
//					}
//				}
//				cuPersonsList.add(stInfo.getStudentId());
//				String cuJoinPersons = String.join(",", cuPersonsList);
//				cuInfo.setPersons(cuJoinPersons);
//				courseInfoDao.save(cuInfo);
//			}
//
//			// 判斷學生目前的學分，確認可以選課學分才增加
//			// 先取得目前學生的學分
//			int totalCredits = 0;
//			totalCredits = stInfo.getCredits();
//
//			// 學生目前的學分加上該選課的學分
//			totalCredits += cuInfo.getCredits();
//			// 總學分不能超過10
//			if (totalCredits > 10) {
//				return new StudentInfoResponse("已超出學分上限");
//			}
//			// 總學分沒超過 10 則調整學生的學分
//			stInfo.setCredits(totalCredits);
//
//			stCourseIdList.add(names);
//			stCourseNameList.add(courseInfoDao.findById(names).get().getCourseName());
//		}
//		stInfo.setCourseId(String.join(",", stCourseIdList));
//		stInfo.setCourseName(String.join(",", stCourseNameList));
//		studentInfoDao.save(stInfo);
//		return new StudentInfoResponse(stInfo, "選課成功");
//	}

//=================================================================================================
	public CourseInfo getCourseInfo(String id) {
		Optional<CourseInfo> cuOp = courseInfoDao.findById(id);
		CourseInfo cuGet = cuOp.get();
		return cuGet;
	}

	public StudentInfo getStudentInfo(StudentInfoRequest request) {
		String studentId = request.getStudentId();
		Optional<StudentInfo> stOp = studentInfoDao.findById(studentId);
		StudentInfo stGet = stOp.get();
		return stGet;
	}

}

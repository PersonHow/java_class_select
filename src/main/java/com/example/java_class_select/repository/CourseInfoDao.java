package com.example.java_class_select.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_class_select.entity.CourseInfo;

@Repository
public interface CourseInfoDao extends JpaRepository<CourseInfo, String>{

	List <CourseInfo> findBycourseName(String name);
}

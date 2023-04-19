package com.example.java_class_select.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_class_select.entity.StudentInfo;

@Repository
public interface StudentInfoDao extends JpaRepository<StudentInfo, String>{

}

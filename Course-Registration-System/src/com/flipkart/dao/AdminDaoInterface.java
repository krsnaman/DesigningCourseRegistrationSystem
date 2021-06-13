package com.flipkart.dao;

public interface AdminDaoInterface {
	public void addStudent(String studID, String password, String studName, String studDept);
	
	public void addProfessor(String profID, String password, String profName, String profDept);
	
	public void viewCourses();
	
	public void addCourse(String courseID, String courseName, String preReq, int cost);
	
	public void removeCourse(String courseID);
	
	public void verifyCourseReg();
}

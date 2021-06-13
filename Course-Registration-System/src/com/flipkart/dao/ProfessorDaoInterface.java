package com.flipkart.dao;

public interface ProfessorDaoInterface {
	public void viewCourse();
	
	public void chooseCourseToTeach(String courseID, String profID);
	
	public boolean viewCourseReg(String profID);
	
	public void updateGrade(String profID, String studID, int intGrade);

}

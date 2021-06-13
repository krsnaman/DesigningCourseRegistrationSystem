package com.flipkart.dao;

public interface StudentDaoInterface {
	public void viewCourse();
	
	public void checkAvailability(String courseID);
	
	public void registerCourse(String studID, String courseID);
	
	public void dropCourse(String studID, String courseID);
	
	public int viewBillToPay(String studID);
	
	public void payFee(String studID);
	
	public void viewReportCard(String studID);

}

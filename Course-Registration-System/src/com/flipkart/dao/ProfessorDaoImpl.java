package com.flipkart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfessorDaoImpl implements ProfessorDaoInterface{
	// JDBC driver name and database URL
	String cID =  "";
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/db";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
   
	public void viewCourse() {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 4 Open make a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql="";
			
			sql = "select cID, cName, preReq from courses where prof is NULL";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				//Retrieve by column name
		         String cID  = rs.getString("cID");
		         String cName = rs.getString("cName");
		         String preReq = rs.getString("preReq");

		         //Display values
		         System.out.print("Course ID: " + cID);
		         System.out.print("| Course Name:" + cName);
		         System.out.println("| Course Pre-Requisites:" + preReq);
			}
			
			stmt.close();
			conn.close();
		}catch(Exception e) {
			System.out.println(e);
		}		
	}
	
	public void chooseCourseToTeach(String courseID, String profID) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
		// Step 3 Register Driver here and create connection 
		Class.forName("com.mysql.jdbc.Driver");
		
		// Step 4 Open make a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String sql = "update courses set prof = ? where cID = ? and prof is NULL";
		
		stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, profID);
		stmt.setString(2, courseID);
		
		stmt.executeUpdate();
		
		System.out.println("You are added as the professor of your chosen course.");
		
		stmt.close();
		conn.close();
		 //or, passwordUpdated.
		}catch(Exception e){
		      //Handle errors for JDBC
		      System.out.println(e);
		}

	}
	
	public boolean viewCourseReg(String profID) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean b = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 4 Open make a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql="";
			
			sql = "select cID from courses where prof=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, profID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
		         cID  = rs.getString("cID");
			}
			
			sql = "select studID from registrations where courseID=? and isVerified=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, cID);
			stmt.setBoolean(2, true);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				b = true;
				//Retrieve by column name
		         String sID  = rs.getString("studID");
		         //Display values
		         System.out.println("Student ID: " + sID);
			}
			
			stmt.close();
			conn.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		return b;
	}
	
	public void updateGrade(String profID, String studID, int intGrade) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
		// Step 3 Register Driver here and create connection 
		Class.forName("com.mysql.jdbc.Driver");
		
		// Step 4 Open make a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String sql = "select cID from courses where prof=?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, profID);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
	         cID  = rs.getString("cID");
		}
		
		sql = "update registrations set gradeInt = ? where studID = ? and courseID=? and isVerified=?";
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, intGrade);
		stmt.setString(2, studID);
		stmt.setString(3, cID);
		stmt.setBoolean(4, true);
		stmt.executeUpdate();
		
		System.out.println("Grade updated for the student "+studID);
		
		stmt.close();
		conn.close();
		 //or, passwordUpdated.
		}catch(Exception e){
		      //Handle errors for JDBC
			System.out.println("Prof can't update grade of a student in a course which has not been verified.");
		      System.out.println(e);
		}
	}

}

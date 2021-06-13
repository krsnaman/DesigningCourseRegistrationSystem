package com.flipkart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDaoImpl implements AdminDaoInterface{
	// JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/db";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
	public void addStudent(String studID, String password, String studName, String studDept) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 4 Open make a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql="";
			
			sql = "insert into users values(?, ?, ?)";
			String tempStr = "Student";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, studID);
			stmt.setString(2, password);
			stmt.setNString(3, tempStr);
			stmt.executeUpdate();
			
			sql = "insert into students values(?, ?, ?, ?)";
			int tempInt = 0;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, studID);
			stmt.setString(2, studName);
			stmt.setString(3, studDept);
			stmt.setInt(4, tempInt);
			stmt.executeUpdate();
			
			System.out.println("Student added.");
			
			stmt.close();
			conn.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	public void addProfessor(String profID, String password, String profName, String profDept) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 4 Open make a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql="";
			
			sql = "insert into users values(?, ?, ?)";
			String tempStr = "Professor";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, profID);
			stmt.setString(2, password);
			stmt.setNString(3, tempStr);
			stmt.executeUpdate();
			
			sql = "insert into professors values(?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, profID);
			stmt.setString(2, profName);
			stmt.setString(3, profDept);
			stmt.executeUpdate();
			
			System.out.println("Professor added.");
			
			stmt.close();
			conn.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void viewCourses() {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 4 Open make a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql="";
			
			sql = "select cID, cName, preReq, numStudReg, cost from courses";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				//Retrieve by column name
		         String cID  = rs.getString("cID");
		         String cName = rs.getString("cName");
		         int cost = rs.getInt("cost");
		         String preReq = rs.getString("preReq");
		         int numStudReg = rs.getInt("numStudReg");

		         //Display values
		         System.out.print("Course ID:" + cID);
		         System.out.print("| Course Name:" + cName);
		         System.out.print("| Course Pre-Requisites:" + preReq);
		         System.out.print("| Course Fee:" + cost);
		         System.out.println("| Number of Reg Stud:"+ numStudReg);
			}
			
			stmt.close();
			conn.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void addCourse(String courseID, String courseName, String preReq, int cost) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 4 Open make a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql="";
			
			sql = "insert into courses(cID, cName, preReq, cost, numStudReg) values(?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, courseID);
			stmt.setString(2, courseName);
			stmt.setString(3, preReq);
			stmt.setInt(4, cost);
			stmt.setInt(5,  0);
			stmt.executeUpdate();
			
			System.out.println("Course Added.");
			
			stmt.close();
			conn.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void removeCourse(String courseID) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 4 Open make a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			String sql="delete from courses where cID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, courseID);
			stmt.executeUpdate();
			
			System.out.println("Course deleted");
			
			stmt.close();
			conn.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void verifyCourseReg() {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
		// Step 3 Register Driver here and create connection 
		Class.forName("com.mysql.jdbc.Driver");
		
		// Step 4 Open make a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String sql = "update registrations set isVerified = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setBoolean(1, true);
		
		stmt.executeUpdate();
		
		System.out.println("You have verified all the registration request.");
		
		stmt.close();
		conn.close();
		 //or, passwordUpdated.
		}catch(Exception e){
		      //Handle errors for JDBC
		      System.out.println(e);
		}
	}
}

package com.flipkart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDaoImpl implements StudentDaoInterface{
	// JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/db";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
   
	@Override
	public void viewCourse() {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 4 Open make a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql="";
			
			sql = "select cID, cName, preReq, pName, dept, cost from courses, professors where prof=pID";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				//Retrieve by column name
		         String cID  = rs.getString("cID");
		         String cName = rs.getString("cName");
		         String preReq = rs.getString("preReq");
		         String profName = rs.getString("pName");
		         String dept = rs.getString("dept");
		         String cost = rs.getString("cost");

		         //Display values
		         System.out.print("Course ID:" + cID);
		         System.out.print("| Course Name:" + cName);
		         System.out.print("| Course Pre-Requisites:" + preReq);
		         System.out.print("| Professor:" + profName);
		         System.out.print("| Department:" + dept);
		         System.out.println("| Course Fee:" + cost);
			}
			
			stmt.close();
			conn.close();
		}catch(Exception e) {
			System.out.println(e);
		}		
		
	}

	@Override
	public void checkAvailability(String courseID) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 4 Open make a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql="";
			
			sql = "select numStudReg from courses where cID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setNString(1, courseID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				//Retrieve by column name
		         int numStudReg  = rs.getInt("numStudReg");

		         //Display values
		         if(numStudReg < 10) {
		        	 System.out.println("Course is available");
		         }
			}
			
			stmt.close();
			conn.close();
		}catch(Exception e) {
			System.out.println(e);
		}		
	}

	@Override
	public void registerCourse(String studID, String courseID) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		int numStudReg = 0, cost = 0, bill = 0;
		
		try {
		// Step 3 Register Driver here and create connection 
		Class.forName("com.mysql.jdbc.Driver");
		
		// Step 4 Open make a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String sql = "select numStudReg, cost from courses where cID = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, courseID);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
	         numStudReg  = rs.getInt("numStudReg");
	         cost = rs.getInt("cost");
	         if(numStudReg == 10) {
	        	 System.out.println("Course is not available anymore. Maximum students enrolled.");
	         }
	         numStudReg++;
		}
		
		sql = "select bill from students where sID = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, studID);
		rs = stmt.executeQuery();
		while(rs.next()) {
	         bill  = rs.getInt("bill");
		}
		bill += cost;
		
		if(numStudReg <= 10) {
			try {
			
			sql = "insert into registrations(studID, courseID) values(?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, studID);
			stmt.setString(2, courseID);
			stmt.executeUpdate();
			
			sql = "update courses set numStudReg = ? where cID = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, numStudReg);
			stmt.setString(2, courseID);
			stmt.executeUpdate();
			
			sql = "update students set bill = ? where sID = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, bill);
			stmt.setString(2, studID);
			stmt.executeUpdate();
			
			System.out.println("You have requested for the registration of the course "+courseID );
			}catch(Exception e) {
				System.out.println("You have already requested to register or you are already registered in "+courseID);
			}
		}
		stmt.close();
		conn.close();
		 //or, passwordUpdated.
		}catch(Exception e){
		      //Handle errors for JDBC
		      System.out.println(e);
		}
	}

	@Override
	public void dropCourse(String studID, String courseID) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		int numStudReg = 0, cost = 0, bill = 0, exist = 0;
		
		try {
		// Step 3 Register Driver here and create connection 
		Class.forName("com.mysql.jdbc.Driver");
		
		// Step 4 Open make a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String sql = "select numStudReg, cost from courses where cID = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, courseID);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
	         numStudReg  = rs.getInt("numStudReg");
	         cost = rs.getInt("cost");
	         if(numStudReg == 10) {
	        	 System.out.println("Course is not available anymore. Maximum students enrolled.");
	         }
	         numStudReg--;
		}
		
		sql = "select bill from students where sID = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, studID);
		rs = stmt.executeQuery();
		while(rs.next()) {
	         bill  = rs.getInt("bill");
		}
		bill -= cost;
		
		sql = "select count(*) as numRows from registrations where studID = ? and courseID = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, studID);
		stmt.setString(2, courseID);
		rs = stmt.executeQuery();
		while(rs.next()) {
	         exist  = rs.getInt("numRows");
		}
		
		if(exist == 1) {
			sql = "delete from registrations where studID=? and courseID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, studID);
			stmt.setString(2, courseID);
			stmt.executeUpdate();
			
			sql = "update courses set numStudReg = ? where cID = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, numStudReg);
			stmt.setString(2, courseID);
			stmt.executeUpdate();
			
			sql = "update students set bill = ? where sID = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, bill);
			stmt.setString(2, studID);
			stmt.executeUpdate();
			
			System.out.println("You have dropped the course "+courseID );
		}
		else {
			System.out.println("You are not registered for the course "+courseID);
		}
		stmt.close();
		conn.close();
		}catch(Exception e){
		      //Handle errors for JDBC
		      System.out.println(e);
		}
	}

	
	public int viewBillToPay(String studID) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		int bill = 0;
		
		try {
		// Step 3 Register Driver here and create connection 
		Class.forName("com.mysql.jdbc.Driver");
		
		// Step 4 Open make a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String sql = "select bill from students where sID = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, studID);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
	         bill  = rs.getInt("bill");
			}
		System.out.println("Your total payable amount is "+bill);
		stmt.close();
		conn.close();
		}catch(Exception e){
		      //Handle errors for JDBC
		      System.out.println(e);
		}
		return bill;
	}
	
	@Override
	public void payFee(String studID) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
		// Step 3 Register Driver here and create connection 
		Class.forName("com.mysql.jdbc.Driver");
		
		// Step 4 Open make a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String sql = "update students set bill = ? where sID = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, 0);
		stmt.setString(2, studID);
		stmt.executeUpdate();
		System.out.println("Your paid your total bill.");
		stmt.close();
		conn.close();
		}catch(Exception e){
		      //Handle errors for JDBC
		      System.out.println(e);
		}
	}

	@Override
	public void viewReportCard(String studID) {
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
		// Step 3 Register Driver here and create connection 
		Class.forName("com.mysql.jdbc.Driver");
		
		// Step 4 Open make a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String sql = "select courseID, gradeInt from registrations where studID = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, studID);
		ResultSet rs = stmt.executeQuery();
		int count = 0, sum = 0;
		while(rs.next()) {
	         String courseID  = rs.getString("courseID");
	         int gradeInt = rs.getInt("gradeInt");
	         if(gradeInt != 0)
	        	 count++;
	         sum += gradeInt;
	         System.out.print("Course ID:"+ courseID);
	         if(gradeInt != 0)
	        	 System.out.println("| grade:"+ gradeInt);
	         else
	        	 System.out.println("| grade:Not been graded yet.");
			}
		try {
			System.out.println("Your GPA is "+sum/(double)count);
		}catch(ArithmeticException e1) {
			System.out.println("No subject has been graded.");
		}
		stmt.close();
		conn.close();
		}catch(Exception e){
		      //Handle errors for JDBC
		      System.out.println(e);
		}
	}
}

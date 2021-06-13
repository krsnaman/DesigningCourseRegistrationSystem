package com.flipkart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;

public class UserDaoImpl implements UserDaoInterface {
		// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/db";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "root";
	
	public String login(String userID, String password){
		
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		String role="";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 4 Open make a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql="";
			
			sql = "select role from users where uID = ? and pass = ?";
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, userID);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
			role = rs.getString("role");
			}
			
			stmt.close();
			conn.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		return role;
		// Step 3 Register Driver here and create connection 
		
	}
	
	public void updatePassword(String userID, String currentPassword, String newPassword){
		// Declare the Connection or prepaidStatement variable here 
		Connection conn = null;
		PreparedStatement stmt = null;
		int flag = 0;
		
		try {
		// Step 3 Register Driver here and create connection 
		Class.forName("com.mysql.jdbc.Driver");
		
		// Step 4 Open make a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String sql = "update users set pass = ? where uID = ? and pass = ?";
		
		stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, newPassword);
		stmt.setString(2, userID);
		stmt.setString(3, currentPassword);
		
		stmt.executeUpdate();
		flag = 1;
		
		stmt.close();
		conn.close();
		 //or, passwordUpdated.
		}catch(Exception e){
		      //Handle errors for JDBC
		      System.out.println(e);
		}
	    if(flag == 1) {
			System.out.println("Password Updated");
	    }
		else {
			System.out.println("There was an issue. Password not updated");
		}
	}
}
	


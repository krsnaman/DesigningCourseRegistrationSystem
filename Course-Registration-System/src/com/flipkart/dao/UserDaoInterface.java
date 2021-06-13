/**
 * 
 */
package com.flipkart.dao;

/**
 * @author DELL
 *
 */
public interface UserDaoInterface {
	public String login(String userID, String password);
	
	public void updatePassword(String userID, String currentPassword, String newPassword);
	
}

package com.flipkart.application;

import java.util.Scanner;

import com.flipkart.bean.User;
import com.flipkart.dao.*;

public class ApplicationMenu {
	static void firstPrint() {
		System.out.println("------------------");
		System.out.println("Press 1 to login");
		System.out.println("Press 2 to change your password");
		System.out.println("Press 3 to exit the application");
		System.out.println("------------------");
	}
	
	static void adminPrint() {
		System.out.println("------------------");
		System.out.println("Press 1 to add student");
		System.out.println("Press 2 to add professor");
		System.out.println("Press 3 to view course");
		System.out.println("Press 4 to add a course");
		System.out.println("Press 5 to remove a course");
		System.out.println("Press 6 to verify a course registration");//view whole registration table & then verify on
																	//the basis of sID, cID and add bill on student table
		System.out.println("Press 7 to go back");
		System.out.println("------------------");
	}
	
	static void professorPrint() {
		System.out.println("------------------");
		System.out.println("Press 1 to view courses");
		System.out.println("Press 2 to choose a course that you want to teach");
		System.out.println("Press 3 to update grade of a student");
		System.out.println("Press 4 to go back");
		System.out.println("------------------");
	}
	
	static void studentPrint() {
		System.out.println("------------------");
		System.out.println("Press 1 to view courses");
		System.out.println("Press 2 to check availability of a course");
		System.out.println("Press 3 to register for a course");
		System.out.println("Press 4 to drop a course");
		System.out.println("Press 5 to pay fee");
		System.out.println("Press 6 to see your report card");
		System.out.println("Press 7 to go back");
		System.out.println("------------------");
	}


	public static void main(String[] args) throws Exception {
		int userInput, adminInput, professorInput, studentInput;
		String pass="", newPass="", newPassConfirm="";
		String role="", uID="";
		Scanner in = new Scanner(System.in);
		while(true) {
			int userFlag = 0;
			firstPrint();
			userInput = in.nextInt();
			
			switch(userInput) {
			case 1: 
				UserDaoInterface u = new UserDaoImpl();
				User uBean =  new User();
				System.out.println("Enter user id:");
				uID = in.next();
				uBean.setUserID(uID);
				System.out.println("Enter password:");
				pass = in.next();
				
				role = u.login(uID, pass);
				
				if(role.equals("Admin")) {
					int adminFlag = 0;
					System.out.println("You logged in as admin");
					AdminDaoInterface a = new AdminDaoImpl();
					while(true) {
						adminPrint();
						adminInput = in.nextInt();
						
						switch(adminInput) {
						case 1:
							System.out.println("Enter student id:");
							String studID = in.next();
							System.out.println("Enter password:");
							String studPassword = in.next();
							System.out.println("Enter student's name:");
							String studName = in.next();
							System.out.println("Enter student's dept");
							String studDept = in.next();
							a.addStudent(studID, studPassword, studName, studDept);
							break;
						
						case 2:
							System.out.println("Enter professor id:");
							String profID = in.next();
							System.out.println("Enter password:");
							String profPassword = in.next();
							System.out.println("Enter professor's name:");
							String profName = in.next();
							System.out.println("Enter professor's dept");
							String profDept = in.next();
							a.addProfessor(profID, profPassword, profName, profDept);
							break;
							
						case 3:
							a.viewCourses();
							break;
							
						case 4:
							System.out.println("Enter course id:");
							String courseID = in.next();
							System.out.println("Enter course name:");
							String courseName = in.next();
							System.out.println("Enter pre-requisites:");
							String preReq = in.next();
							System.out.println("Enter course fee:");
							int cost = in.nextInt();
							a.addCourse(courseID, courseName, preReq, cost);
							break;
						
						case 5:
							System.out.println("Enter course id:");
							String courseDeleteID = in.next();
							a.removeCourse(courseDeleteID);
							break;
							
						case 6:
							a.verifyCourseReg();
							break;
							
						case 7:
							adminFlag = 1;
							break;
						}
						if(adminFlag == 1) {
							System.out.println("Exiting from Admin interface.");
							break;
						}
					}
					
					
					
				}
				else if(role.contentEquals("Professor")) {
					int professorFlag = 0;
					System.out.println("You logged in as professor");
					ProfessorDaoInterface p = new ProfessorDaoImpl();
					while(true) {
						professorPrint();
						professorInput = in.nextInt();
						String profID = uBean.getUserID();
						
						switch(professorInput) {
						case 1:
							p.viewCourse();
							break;
							
						case 2:
							p.viewCourse();
							System.out.println("Enter the courseID of the course you want to teach:");
							String courseToTeachID = in.next();
							p.chooseCourseToTeach(courseToTeachID, profID);
							break;
							
						case 3:
							boolean b = p.viewCourseReg(profID);
							if(b) {
								System.out.println("Enter student ID of student whose grade you want to update:");
								String studID = in.next();
								System.out.println("Enter the grade of the chosen student:");
								int intGrade = in.nextInt();
								p.updateGrade(profID, studID, intGrade);
							}
							else {
								System.out.println("No students to update.");
							}
							break;
							
						case 4:
							professorFlag = 1;
							break;
						}
						if(professorFlag == 1) {
							System.out.println("Exiting from Profesor Interface.");
							break;
						}
					}
					
				}
				else if(role.equals("Student")) {
					int studentFlag = 0;
					System.out.println("You logged in as Student.");
					StudentDaoInterface s = new StudentDaoImpl();
					
					while(true) {
						studentPrint();
						studentInput = in.nextInt();
						String studID = uBean.getUserID();
						
						switch(studentInput) {
						case 1:
							s.viewCourse();
							break;
						
						case 2:
							System.out.println("Enter the courseID for which you want to check availability:");
							String courseID = in.next();
							s.checkAvailability(courseID);
							break;
							
						case 3:
							System.out.println("Enter the courseID for which you want to register:");
							courseID = in.next();
							s.registerCourse(studID, courseID);
							break;
							
						case 4:
							System.out.println("Enter the courseID of the course you want to drop:");
							courseID = in.next();
							s.dropCourse(studID, courseID);
							break;
							
						case 5:
							int bill = s.viewBillToPay(studID);
							if(bill > 0) {
								System.out.println("Enter 1 to pay UPI ID; 2 to pay using card: 3 if you have scholarship.");
								int payOption = in.nextInt();
								if(payOption == 1) {
									System.out.println("Enter your UPI ID:");
									String upiID = in.next();
									System.out.println("Verify the payment in your UPI app.");
									s.payFee(studID);
								}
								else if(payOption == 2) {
									System.out.println("Enter your card number:");
									int cardNum = in.nextInt();
									s.payFee(studID);
								}
								else if(payOption == 3) {
									System.out.println("Enter the scholarship number:");
									int scholarshipNum = in.nextInt();
									s.payFee(studID);
								}
								else {
									System.out.println("Invalid Input.");
								}
							}
							else if(bill < 0) {
								System.out.println("You have an advance payment of "+java.lang.Math.abs(bill)+" because you dropped one or more course.");
							}
							else {
								System.out.println("You have nothing due to pay.");
							}

							break;
							
						case 6:
							s.viewReportCard(studID);
							break;
							
						case 7:
							studentFlag = 1;
							break;
						}
						if(studentFlag == 1) {
							System.out.println("Exiting from Student Interface.");
							break;
						}
					}
				}
				break;
				
			case 2:
				UserDaoInterface u1 = new UserDaoImpl();
				System.out.println("Enter user id:");
				uID = in.next();
				System.out.println("Enter current password");
				pass = in.next();
				System.out.println("Enter new password");
				newPass = in.next();
				System.out.println("Confirm new password");
				newPassConfirm = in.next();
				
				if(newPass.equals(newPassConfirm)) {
					u1.updatePassword(uID, pass, newPass);
				}
				else {
					System.out.println("Passwords don't match");
				}
				break;
				
			case 3:
				userFlag = 1;
				System.out.println("Exiting from the Application");
				break;
			}
			if(userFlag == 1) {
				in.close();
				break;
			}
		}

	}

}

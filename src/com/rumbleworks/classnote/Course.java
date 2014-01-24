package com.rumbleworks.classnote;

import java.util.ArrayList;

public class Course {

	String courseName;
	String courseSchool;
	int courseNumber;
	
	ArrayList<GradedWork> gradedWorkList;
	ArrayList<Announcement> announcementList;
	
	/** @param String courseName, String courseSchool, int courseNumber
	 * A course is a course at a university or college.  Here, it exists as a set of information 
	 * important for identifying which course it is, and it also contains two ArrayLists
	 * one for objects that inherit from Gradedwork (tests and assignments)
	 * and the other for Announcements from the professor
	 *  @return void */ 
	
	public Course( String courseName, String courseSchool, int courseNumber ) {
		this.courseName = courseName;
		this.courseSchool = courseSchool;
		this.courseNumber = courseNumber;
		
		this.gradedWorkList = new ArrayList<GradedWork>();
		this.announcementList = new ArrayList<Announcement>();
		
	}
	
}

package com.rumbleworks.classnote;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
	private String courseSchool;
	private int courseNumber;
	
	private ArrayList<Assignment> assignmentList;
	private ArrayList<Announcement> announcementList;
	
	/** @param String courseName, String courseSchool, int courseNumber
	 * A course is a course at a university or college.  Here, it exists as a set of information 
	 * important for identifying which course it is, and it also contains two ArrayLists
	 * one for objects that inherit from Gradedwork (tests and assignments)
	 * and the other for Announcements from the professor
	 *  @return void */ 
	
	public Course(String courseName, String courseSchool, int courseNumber) {
		this.courseName = courseName;
		this.courseSchool = courseSchool;
		this.courseNumber = courseNumber;
		
		this.assignmentList = new ArrayList<Assignment>();
		this.announcementList = new ArrayList<Announcement>();
	}

    public String getCourseName() {
        return courseName;
    }

    public String getCourseSchool() {
        return courseSchool;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }

    public void addAssignment(Assignment assignment) {
        assignmentList.add(assignment);
    }

    public void addAnnouncement(Announcement announcement) {
        announcementList.add(announcement);
    }


}

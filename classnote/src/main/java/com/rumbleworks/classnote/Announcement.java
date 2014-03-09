package com.rumbleworks.classnote;

import java.util.Date;

/**
 * An Announcement is one of the T-Square, from-teacher notifications that are not actually assignments
 * but should be visible within the list function, thus it implements DataItem so we know we can
 * show it in our upcoming list
 */

public class Announcement {

	public String name;
	public String description;
	public Boolean isRead;
	public Date dueDate;
	public int courseNumber;
	
	public Announcement(String name, String description, Boolean isRead, Date dueDate, int courseNumber) {
		this.name = name;
		this.description = description;
		this.isRead = isRead;
		this.dueDate = dueDate;
		this.courseNumber = courseNumber;
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}

	
	public Boolean getIsRead() {
		// TODO Auto-generated method stub
		return isRead;
	}

	
	public Date getDueDate() {
		// TODO Auto-generated method stub
		return dueDate;
	}
	
	
	public int getCourseNumber() {
		// TODO Auto-generated method stub
		return courseNumber;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	
	public void setCourseNumber(int courseNumber) {
		this.courseNumber = courseNumber;
	}

}

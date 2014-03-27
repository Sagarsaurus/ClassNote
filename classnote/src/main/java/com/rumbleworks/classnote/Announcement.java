package com.rumbleworks.classnote;

import java.io.Serializable;
import java.util.Date;

/**
 * An Announcement is one of the T-Square, from-teacher notifications that are not actually assignments
 * but should be visible within the list function, thus it implements DataItem so we know we can
 * show it in our upcoming list
 */

public class Announcement implements Comparable<Announcement>, Serializable {

	public String name;
	public String description;
	public Boolean isRead;
	public Date dueDate;
	public String courseName;

//    //Temporary description-only constructor
//    //public Announcement(String description) {
//        this("Nil", description, false, new Date(), " ");
//    }

	public Announcement(String name, String description, Boolean isRead, Date dueDate, String courseName) {
		this.name = name;
		this.description = description;
		this.isRead = isRead;
		this.dueDate = dueDate;
		this.courseName = courseName;
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
	
	
	public String getCourseName() {
		// TODO Auto-generated method stub
		return courseName;
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

	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

    @Override
    public int compareTo(Announcement announcement) {
        return dueDate.compareTo(announcement.dueDate);
    }

}

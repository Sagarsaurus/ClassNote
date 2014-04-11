package com.rumbleworks.classnote;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
	public Course course;
    public String id;

//    //Temporary description-only constructor
//    //public Announcement(String description) {
//        this("Nil", description, false, new Date(), " ");
//    }

	public Announcement(String id, String name, String description, Boolean isRead, Date dueDate) {
        this.id = id;
		this.name = name;
		this.description = description;
		this.isRead = isRead;
		this.dueDate = dueDate;
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
	
	
	public Course getCourse() {
		// TODO Auto-generated method stub
		return course;
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

	
	public void setCourse(Course course) {
		this.course = course;
	}

    @Override
    public int compareTo(Announcement announcement) {
        return dueDate.compareTo(announcement.dueDate);
    }

    public boolean equals(Object object) {
        return object instanceof Announcement && ((Announcement)object).getId().equals(this.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

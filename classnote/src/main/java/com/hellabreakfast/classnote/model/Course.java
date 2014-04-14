package com.hellabreakfast.classnote.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A course is a course at a university or college.  Here, it exists as a set of information
 * important for identifying which course it is, and it also contains two ArrayLists
 * one for objects that inherit from GradedWork (tests and assignments)
 * and the other for Announcements from the professor
 */

public class Course implements Serializable {

    private String title, siteId;

	private ArrayList<Assignment> assignmentList;
	private ArrayList<Announcement> announcementList;
	
	public Course(String title, String siteId) {
		this.title = title;
		this.siteId = siteId;

		this.assignmentList = new ArrayList<Assignment>();
		this.announcementList = new ArrayList<Announcement>();
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }

    public void addAssignment(Assignment assignment) {
        if (assignment.getId() != null) {
            for (Assignment a : assignmentList) {
                if (a.getId().equals(assignment.getId())) return;
            }
        }
        assignment.setCourse(this);
        assignmentList.add(assignment);
    }

    public void addAnnouncement(Announcement announcement) {
        announcement.setCourse(this);
        announcementList.add(announcement);
    }

    public boolean hasAnnouncement(Announcement announcement) {
        if (announcementList.contains(announcement))
            return true;
        else
            return false;
    }
}

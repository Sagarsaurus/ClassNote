package com.rumbleworks.classnote;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Datamart {

    static Datamart instance;

	private ArrayList<Course> courseList;

    //Temporary to hold the announcements
    private ArrayList<Announcement> announcements;
	
	public Datamart() {
		courseList = new ArrayList<Course>();
        announcements = new ArrayList<Announcement>();
        courseList.add(new Course("Junior Design 2", "CS", 3802));
        courseList.add(new Course("Intro to Networking", "CS", 3251));
        courseList.add(new Course("Art History II", "COA", 2242));
        announcements.add(new Announcement("Stuff Happened", "So, things and stuff. Also, other things.", false, new Date(0), 0));
        announcements.add(new Announcement("More Things!", "These things are so great! Oh my god!", false, new Date(86400000), 0));
    }

    public static Datamart getInstance() {
        if (instance == null) {
            instance = new Datamart();
        }
        return instance;
    }

    public List<String> getCourseIds() {
        ArrayList<String> courseIds = new ArrayList<String>();
        for (Course c : courseList) {
            courseIds.add(c.getCourseId());
        }
        return courseIds;
    }

    public Course getCourseById(String courseId) {
        for (Course c : courseList) {
            if (c.getCourseId().equals(courseId)) return c;
        }
        return null;
    }

    public List<Assignment> getAllAssignments() {
        List<Assignment> list = new LinkedList<Assignment>();
        for (Course c : getCourseList()) {
            list.addAll(c.getAssignmentList());
        }
        return list;
    }

    public List<Assignment> getUpcomingAssignments() {
        List<Assignment> list = new LinkedList<Assignment>();
        for (Course c : getCourseList()) {
            for (Assignment a : c.getAssignmentList()) {
                if (a.getDueDate().after(new Date())) {
                    list.add(a);
                }
            }
        }
        return list;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void addCourse(Course c) {
        courseList.add(c);
    }

    /**
     * Returns ArrayList if announcements
     * @return
     */
    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * Add announcement to the arraylist
     */
    public void addAnnouncement(String ann) {
        announcements.add(new Announcement(ann));
    }
}

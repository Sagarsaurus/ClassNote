package com.rumbleworks.classnote;

import java.util.ArrayList;
import java.util.List;

public class Datamart {

    static Datamart instance;

	private ArrayList<Course> courseList;
	
	public Datamart() {
		courseList = new ArrayList<Course>();
        courseList.add(new Course("Junior Design 2", "CS", 3802));
        courseList.add(new Course("Intro to Networking", "CS", 3251));
        courseList.add(new Course("Art History II", "COA", 2242));
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
            if (c.getCourseId() == courseId) return c;
        }
        return null;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void addCourse(Course c) {
        courseList.add(c);
    }
}

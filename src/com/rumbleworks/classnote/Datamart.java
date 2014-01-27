package com.rumbleworks.classnote;

import java.util.ArrayList;
import java.util.List;

public class Datamart {

    static Datamart instance;

	private ArrayList<Course> courseList;
	
	public Datamart() {
		courseList = new ArrayList<Course>();
	}

    public static Datamart getInstance() {
        if (instance == null) {
            instance = new Datamart();
        }
        return instance;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void addCourse(Course c) {
        courseList.add(c);
    }
}

package com.rumbleworks.classnote;

import android.app.Activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Datamart implements Serializable {

    static String SAVE_FILE_NAME = "datamart-serialized.txt";

    static Datamart instance;

	private ArrayList<Course> courseList;

    private boolean[] visited = { false, false, false, false, false, true, true };
    private int currentScreen = 0;

    //Temporary to hold the announcements
    private ArrayList<Announcement> announcements;

	public Datamart() {
        courseList = new ArrayList<Course>();
        announcements = new ArrayList<Announcement>();
        courseList.add(new Course("Junior Design 2", "CS", 3802));
        courseList.add(new Course("Intro to Networking", "CS", 3251));
        courseList.add(new Course("Art History II", "COA", 2242));
    }

    public static Datamart getInstance() {
        if (instance == null) {
            instance = load();
            if (instance == null || !(instance instanceof Datamart)) {
                instance = new Datamart();
            }
        }
        return instance;
    }

    public void save() {
        ObjectOutputStream objectOut = null;
        try {

            FileOutputStream fileOut = ClassNoteApp.getApplication().getApplicationContext().openFileOutput(SAVE_FILE_NAME, Activity.MODE_PRIVATE);

            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            fileOut.getFD().sync();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static Datamart load() {
        ObjectInputStream objectIn = null;
        Object object = null;
        try {
            ClassNoteApp application = ClassNoteApp.getApplication();
            FileInputStream fileIn = application.getApplicationContext().openFileInput(SAVE_FILE_NAME);
            objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();

        } catch (FileNotFoundException e) {
            // Do nothing
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }

        return (Datamart)object;

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
        save();
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
    public void addAnnouncement(String title, String description, Date date, String site) {
        for (Announcement announcement : announcements) {
            if (title.equals(announcement.getName()) && date.equals(announcement.getDueDate()) && site.equals(announcement.getCourseName())) {
                return;
            }
        }
        announcements.add(new Announcement(title, description, false, date, site));
        save();
    }

    public boolean[] getVisited() {
        return visited;
    }
    public void setVisited( int index, boolean value ) {
        visited[ index ] = value;
        save();
    }


    public int getCurrentScreen() {
        return currentScreen;
    }
    public void setCurrentScreen( int currentScreen ) {

        this.currentScreen = currentScreen;
    }
}

package com.rumbleworks.classnote.model;

import android.app.Activity;

import com.rumbleworks.classnote.ui.ClassNoteApp;

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
import java.util.Observable;

public class Datamart extends Observable implements Serializable {

    static String SAVE_FILE_NAME = "datamart-classnote";

    static Datamart instance;

	private ArrayList<Course> courseList;

    private Date lastRefreshed;

    private String username;

    private boolean offline;

    private boolean[] visited = { false, false, false, false, false, true, true };
    private int currentScreen = 0;

	public Datamart() {
        courseList = new ArrayList<Course>();
    }

    public static boolean isLoggedIn() {
        return getInstance().getUsername() != null;
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

    public static void clearInstance() {
        ClassNoteApp.getApplication().deleteFile(SAVE_FILE_NAME);
        instance = null;
    }

    public void save() {
        this.setChanged();
        this.notifyObservers();
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getCourseTitles() {
        ArrayList<String> courseTitles = new ArrayList<String>();
        for (Course c : courseList) {
            courseTitles.add(c.getTitle());
        }
        return courseTitles;
    }

    public Course getCourseByTitle(String title) {
        for (Course c : courseList) {
            if (c.getTitle().equals(title)) return c;
        }
        return null;
    }

    /**
     * Getter for the announcements through the course
     * @return
     */
    public List<Announcement> getAllAnnouncements() {
        List<Announcement> list = new LinkedList<Announcement>();
        for (Course c : getCourseList()) {
            list.addAll(c.getAnnouncementList());
        }
        return list;
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
                if (a != null && a.getDueDate().after(new Date())) {
                    list.add(a);
                }
            }
        }
        return list;
    }

    public List<Assignment> getPastAssignments() {
        List<Assignment> list = new LinkedList<Assignment>();
        for (Course c : getCourseList()) {
            for (Assignment a : c.getAssignmentList()) {
                if (a != null && a.getDueDate().before(new Date())) {
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
        for (Course course : courseList) {
            if (course.getSiteId().equals(c.getSiteId())) return;
        }
        courseList.add(c);
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

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public Date getLastRefreshed() {
        return lastRefreshed;
    }

    public void setLastRefreshed(Date lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }
}

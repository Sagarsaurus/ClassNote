package com.hellabreakfast.classnote.model;

import android.app.Activity;

import com.hellabreakfast.classnote.ui.ClassNoteApp;

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

/**
 * The datamart is a singleton object that contains all data in our application. It is serializable
 * so it can be saved to disk.
 */
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

    /**
     * @return whether a user is logged in
     */
    public static boolean isLoggedIn() {
        return getInstance().getUsername() != null;
    }

    /**
     * @return the singleton instance of the Datamart
     */
    public static Datamart getInstance() {
        if (instance == null) {
            instance = load();
            if (instance == null || !(instance instanceof Datamart)) {
                instance = new Datamart();
            }
        }
        return instance;
    }

    /**
     * deletes the instance from the disk. (used for logout)
     */
    public static void clearInstance() {
        ClassNoteApp.getApplication().deleteFile(SAVE_FILE_NAME);
        instance = null;
    }

    /**
     * saves the instance to disk.
     */
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

    /**
     * @return loads the model from disk and returns it
     */
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

    /**
     * @return the titles for the courses
     */
    public List<String> getCourseTitles() {
        ArrayList<String> courseTitles = new ArrayList<String>();
        for (Course c : courseList) {
            courseTitles.add(c.getTitle());
        }
        return courseTitles;
    }

    /**
     * @param title
     * @return the course object with the specified title
     */
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

    /**
     * Getter for the assignments through the course
     * @return
     */
    public List<Assignment> getAllAssignments() {
        List<Assignment> list = new LinkedList<Assignment>();
        for (Course c : getCourseList()) {
            list.addAll(c.getAssignmentList());
        }
        return list;
    }

    /**
     * Getter for upcoming assignments through the course
     * @return
     */
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

    /**
     * Getter for past assignments through the course
     * @return
     */
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

    /**
     * adds the course to the datamart
     * @param c The course to add
     */
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

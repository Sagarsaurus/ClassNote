package com.rumbleworks.classnote;

import android.app.Activity;

import org.json.JSONObject;

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

    static String SAVE_FILE_NAME = "datamart-serialized.txt";

    static Datamart instance;

	private ArrayList<Course> courseList;
    private ArrayList<JSONObject> propsObject;


    private boolean[] visited = { false, false, false, false, false, true, true };
    private int currentScreen = 0;

    //Temporary to hold the announcements
    private ArrayList<Announcement> announcements;

	public Datamart() {
        courseList = new ArrayList<Course>();
        announcements = new ArrayList<Announcement>();
        propsObject = new ArrayList<JSONObject>();
    }

    public static Datamart getInstance() {
        if (instance == null) {
            //instance = load();
            if (instance == null || !(instance instanceof Datamart)) {
                instance = new Datamart();
            }
        }
        return instance;
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

    public List<Course> getCourseList() {
        return courseList;
    }

    public void addCourse(Course c) {
        for (Course course : courseList) {
            if (course.getSiteId().equals(c.getSiteId())) return;
        }
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
    public void addAnnouncement(String title, String description, Date date, String site) {
        for (Announcement announcement : announcements) {
            if (title.equals(announcement.getName()) && date.equals(announcement.getDueDate()) && site.equals(announcement.getCourseName())) {
                return;
            }
        }
        announcements.add(new Announcement(title, description, false, date, site));
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

    public ArrayList<JSONObject> getPropsObject() {
        return propsObject;
    }

    public void setPropsObject( ArrayList<JSONObject> propsObject) {
        this.propsObject = propsObject;
    }

    public void addPropsObject( JSONObject jsonObject ) {
        this.propsObject.add(jsonObject);
    }

}

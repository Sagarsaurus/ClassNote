package com.hellabreakfast.classnote.model;

import java.io.Serializable;
import java.util.Date;

/**
 * An assignment is a normal, t-square assignment that will be shown in the assignments list
 * and be able to be added to the final grade of the course
 */

public class Assignment extends GradedWork implements Comparable<Assignment>, Serializable {
    private String id;

	public Assignment(String id, String name, String description, Boolean isRead, Date dueDate) {
		super(name, description, isRead, dueDate, 0,0,0,0);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public int compareTo(Assignment assignment) {
        return dueDate.compareTo(assignment.dueDate);
    }

}

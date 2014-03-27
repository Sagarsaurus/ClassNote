package com.rumbleworks.classnote;

import java.io.Serializable;
import java.util.Date;

/**
 * An assignment is a normal, t-square assignment that will be shown in the assignments list
 * and be able to be added to the final grade of the course
 */

public class Assignment extends GradedWork implements Comparable<Assignment>, Serializable {
	
	public Assignment(String name, String description, Boolean isRead, Date dueDate, String courseId, double pointsEarned, double pointsPossible, double overallScore, double percentageOfFinal) {
		super(name, description, isRead, dueDate, courseId, pointsEarned, pointsPossible, overallScore, percentageOfFinal);
        Course c = Datamart.getInstance().getCourseById(courseId);
        if (c == null) throw new IllegalArgumentException("Invalid course ID");
        c.addAssignment(this);
	}

    @Override
    public int compareTo(Assignment assignment) {
        return dueDate.compareTo(assignment.dueDate);
    }

}

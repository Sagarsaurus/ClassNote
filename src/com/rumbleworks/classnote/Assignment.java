package com.rumbleworks.classnote;

import java.util.Date;

/**
 * An assignment is a normal, t-square assignment that will be shown in the assignments list
 * and be able to be added to the final grade of the course
 */

public class Assignment extends GradedWork {
	
	public Assignment(String name, String description, Boolean isRead, Date dueDate, String courseId, double pointsEarned, double pointsPossible, double overallScore, double percentageOfFinal) {
		super(name, description, isRead, dueDate, courseId, pointsEarned, pointsPossible, overallScore, percentageOfFinal);
        Course c = Datamart.getInstance().getCourseById(courseId);
        c.addAssignment(this);
	}
	
}

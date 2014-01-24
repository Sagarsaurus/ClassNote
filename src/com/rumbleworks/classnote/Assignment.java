package com.rumbleworks.classnote;

import java.util.Date;

public class Assignment extends GradedWork {

	/** @param  String name, String description, Boolean isRead, Date dueDate, int courseNumber, double pointsEarned, double pointsPossible, double overallScore, double percentageOfFinal
	 * An assignment is a normal, t-square assignment that will be shown in the assignments list
	 * and be able to be added to the final grade of the course
	 *  @return void */ 
	
	public Assignment( String name, String description, Boolean isRead, Date dueDate, int courseNumber, double pointsEarned, double pointsPossible, double overallScore, double percentageOfFinal ) {
		super( name, description, isRead, dueDate, courseNumber, pointsEarned, pointsPossible, overallScore, percentageOfFinal );
	}
	
}

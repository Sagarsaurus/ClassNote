package com.rumbleworks.classnote;

import java.util.Date;

public class Test extends GradedWork {

	public Test(String name, String description, Boolean isRead, Date dueDate, String courseId, double pointsEarned, double pointsPossible, double overallScore, double percentageOfFinal ) {
		super(name, description, isRead, dueDate, courseId, pointsEarned, pointsPossible, overallScore, percentageOfFinal );
	}
}

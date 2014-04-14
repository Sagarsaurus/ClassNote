package com.rumbleworks.classnote.model;

import java.util.Date;

public class Test extends GradedWork {

	public Test(String name, String description, Boolean isRead, Date dueDate, double pointsEarned, double pointsPossible, double overallScore, double percentageOfFinal ) {
		super(name, description, isRead, dueDate, pointsEarned, pointsPossible, overallScore, percentageOfFinal );
	}
}

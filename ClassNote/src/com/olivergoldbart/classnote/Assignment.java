package com.olivergoldbart.classnote;

import java.sql.Date;

public class Assignment {

	private Course course;
	private String name, description;
	private Date dueDate;
	
	public Assignment( Course course, String name, String description, Date dueDate ) {
		this.course = course;
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
	}
}

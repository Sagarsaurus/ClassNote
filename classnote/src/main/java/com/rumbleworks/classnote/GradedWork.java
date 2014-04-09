package com.rumbleworks.classnote;

import java.io.Serializable;
import java.util.Date;

public class GradedWork implements Serializable {

	public String name;
	public String description;
	public Boolean isRead;
	public Date dueDate;
    public Course course;

	public double pointsEarned;
	public double pointsPossible;
	public double overallScore;
	public double percentageOfFinal;

	public GradedWork(String name, String description, Boolean isRead, Date dueDate, double pointsEarned, double pointsPossible, double overallScore, double percentageOfFinal) {
		this.name = name;
		this.description = description;
		this.isRead = isRead;
		this.dueDate = dueDate;
		this.pointsEarned = pointsEarned;
		this.pointsPossible = pointsPossible;
		this.overallScore = overallScore;
		this.percentageOfFinal = percentageOfFinal;
	}


	public String getName() {
		// TODO Auto-generated method stub
        return name;
	}


	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}


	public Boolean getIsRead() {
		// TODO Auto-generated method stub
		return isRead;
	}


	public Date getDueDate() {
		// TODO Auto-generated method stub
		return dueDate;
	}


	public Course getCourse() {
		return course;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public void setCourse(Course course) {
		this.course = course;
	}


	public double getPointsEarned() {
		// TODO Auto-generated method stub
		return pointsEarned;
	}

	public double getPointsPossible() {
		// TODO Auto-generated method stub
		return pointsPossible;
	}

	public double getOverallScore() {
		// TODO Auto-generated method stub
		return overallScore;
	}

	public double getPercentageOfFinal() {
		// TODO Auto-generated method stub
		return percentageOfFinal;
	}

	public void setPointsEarned(double pointsEarned) {
		this.pointsEarned = pointsEarned;
	}

	public void setPointsPossible(double pointsPossible) {
		this.pointsPossible = pointsPossible;
	}

	public void setOverallScore(double overallScore) {
		this.overallScore = overallScore;
	}

	public void setPercentageOfFinal(double percentageOfFinal) {
		this.percentageOfFinal = percentageOfFinal;
	}
}

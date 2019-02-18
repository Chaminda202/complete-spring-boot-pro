package com.rest.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Mark {
	@JsonProperty("student_id")
	private Integer studId;
	private String grade;
	private int marks;
	private int place;
	
	public Mark() {
		super();
	}
	public Mark(Integer studId, String grade, int marks, int place) {
		super();
		this.studId = studId;
		this.grade = grade;
		this.marks = marks;
		this.place = place;
	}
	public Integer getStudId() {
		return studId;
	}
	public void setStudId(Integer studId) {
		this.studId = studId;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
}

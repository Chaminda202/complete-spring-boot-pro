package com.rest.security.model;

import com.rest.security.util.JacksonUtil;

public class StudentDetails {
	private Student student;
	private Mark mark;
	public StudentDetails() {
		super();
	}
	public StudentDetails(Student student, Mark mark) {
		super();
		this.student = student;
		this.mark = mark;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Mark getMark() {
		return mark;
	}
	public void setMark(Mark mark) {
		this.mark = mark;
	}
	@Override
	public String toString() {
		return JacksonUtil.getToString(this);
	}
}

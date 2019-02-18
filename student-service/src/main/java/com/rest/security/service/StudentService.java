package com.rest.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rest.security.model.Student;

@Service
public class StudentService {
	private final Map<Integer, Student> stulist = new HashMap<>();
	
	{
		stulist.put(1, new Student(1, "Saman", 12));
		stulist.put(2, new Student(2, "Kamal", 11));
		stulist.put(3, new Student(3, "Amal", 10));
		stulist.put(4, new Student(4, "Nimal", 13));
		stulist.put(5, new Student(5, "Amil", 15));
	}
	
	public List<Student> getAllStudent(){
		return stulist.values().stream()
				.collect(Collectors.toList());
	}
	
	/*@HystrixCommand(fallbackMethod = "getDefaultStudentById", commandProperties= {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
	}, ignoreExceptions={RuntimeException.class})*/
	public Student getStudentById(Integer stuId){
		Student student = stulist.get(stuId);
		if(student != null)
			return stulist.get(stuId);
		else
			throw new RuntimeException("Student Record Not found :- " + stuId);
	}
	
	@SuppressWarnings("unused")
	private Student getDefaultStudentById(Integer stuId){
		return new Student(100, "Default student", 100);
	}
} 

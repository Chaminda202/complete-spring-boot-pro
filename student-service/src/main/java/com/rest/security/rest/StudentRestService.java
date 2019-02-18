package com.rest.security.rest;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rest.security.model.Student;
import com.rest.security.service.StudentService;

@RestController
public class StudentRestService {
	private StudentService studentService;
	private RestTemplate restTemplate;

	public StudentRestService(StudentService studentService, RestTemplate restTemplate) {
		this.studentService = studentService;
		this.restTemplate = restTemplate;
	}

	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public List<Student> getAllStudent() {
		return studentService.getAllStudent();
	}

	@RequestMapping(value = "/student/{stuId}", method = RequestMethod.GET)
	public Student getStudentById(@PathVariable Integer stuId) {
		return studentService.getStudentById(stuId);
	}

	@RequestMapping(value = "/student/test", method = RequestMethod.GET)
	public void consumeGetAllStudentService() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<List<Student>> response = restTemplate.exchange("http://localhost:8680/rest/student",
				HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Student>>() {
				});
		List<Student> employees = response.getBody();
		employees.forEach(item -> {
			System.out.println(item.getName() + " " + item.getAge());
		});
	}

	@RequestMapping(value = "/student/test1", method = RequestMethod.GET)
	public void consumeGetAllStudentServiceTe() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<Student[]> response = restTemplate.exchange("http://localhost:8680/rest/student", HttpMethod.GET,
				requestEntity, Student[].class);
		Student[] employees = response.getBody();
		for (Student item : employees)
			System.out.println(item.getName() + " " + item.getAge());
	}
}

package com.rest.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rest.security.model.Mark;
import com.rest.security.model.Student;
import com.rest.security.model.StudentDetails;


@Service
public class StudentDetailsService {
	private Logger logger;
	private RestTemplate restTemplate;
	private StudentService studentService;
	@Autowired
	//private LoadBalancerClient loadBalancerClient;
	
	/*@Autowired
	private DiscoveryClient discoveryClient;*/

	public StudentDetailsService(RestTemplate restTemplate, StudentService studentService) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.restTemplate = restTemplate;
		this.studentService = studentService;
	}

	@HystrixCommand(fallbackMethod = "getDefaultAllStudentDetails",
			commandKey="StudentDtailsWithMark",
			groupKey="student-service",
			commandProperties= {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "50000"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "5")
	})
	public List<StudentDetails> getAllStudentDetails() {
		List<StudentDetails> response = new ArrayList<>();
		studentService.getAllStudent().forEach(item -> {
			StudentDetails details = new StudentDetails();
			details.setStudent(item);
			Mark mark = null;
			try {
				mark = getMarkById(item.getId());
				details.setMark(mark);
			} catch (Exception e) {
				logger.error("Error retrive mark {} -> {}", item.getId(), e.getMessage());
				details.setMark(null);
			}
			/*Mark mark = getMarkById(item.getId()); 
			details.setMark(mark);*/
			response.add(details);
		});
		return response;
	}

	@SuppressWarnings("unused")
	private List<StudentDetails> getDefaultAllStudentDetails() {
		logger.info("Call getDefaultAllStudentDetails ");
		List<StudentDetails> list = new ArrayList<>();
		StudentDetails details = new StudentDetails();
		details.setStudent(new Student(100, "Default student", 100));
		details.setMark(new Mark(0, "F", 0, 0));
		list.add(details);
		return null;
	}
	@HystrixCommand(fallbackMethod = "getDefaultMarkById", 
		commandProperties= {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
		})
	public Mark getMarkById(Integer id){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		
		/*List<ServiceInstance> serviceInstances = discoveryClient.getInstances("result-service");
		String daseUrl = serviceInstances.get(0).getUri().toString();*/
		
		/*ServiceInstance serviceInstance = loadBalancerClient.choose("result-service");
		String daseUrl = serviceInstance.getUri().toString();*/

		String daseUrl = "http://result-service";
		System.out.println("Result service url : "+daseUrl);
		String resultUrl = daseUrl + "/mark/{id}";
		logger.info("BEFORE");
		ResponseEntity<Mark> response = restTemplate.exchange(resultUrl, HttpMethod.GET,
				requestEntity, Mark.class, id);
		logger.info("AFTER");
		return response.getBody();
	}

	@SuppressWarnings("unused")
	private Mark getDefaultMarkById(Integer id){
		logger.info("Call default retrive mark {}", id);
		return new Mark(0, "F", 0, 0);
	}

	@HystrixCommand(fallbackMethod = "getDefaultStudentById", commandProperties= {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "50000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "5")
	})
	public StudentDetails getStudentById(Integer id) {
		StudentDetails details = new StudentDetails();
		Student student = studentService.getStudentById(id);
		if (student != null) {
			details.setStudent(student);
			details.setMark(getMarkById(student.getId()));
		}
		return details;
	}

	@SuppressWarnings("unused")
	private StudentDetails getDefaultStudentById(Integer id) {
		StudentDetails details = new StudentDetails();
		details.setStudent(new Student(100, "Default student", 100));
		details.setMark(new Mark(0, "F", 0, 0));
		return details;
	}
}

package com.rest.security.rest;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.security.configuration.ConfigurationDetails;
import com.rest.security.model.StudentDetails;
import com.rest.security.service.StudentDetailsService;

@RestController
public class StudentDetailsRestService {
	private StudentDetailsService studentDetailsService;	
	private ConfigurationDetails configurationDetails;

	public StudentDetailsRestService(StudentDetailsService studentDetailsService, ConfigurationDetails configurationDetails){
		this.studentDetailsService = studentDetailsService;
		this.configurationDetails = configurationDetails;
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public @ResponseBody List <StudentDetails> getAllStudentDetails(){
		return studentDetailsService.getAllStudentDetails();
	}
	
	@RequestMapping(value="/detail/{stuId}", method=RequestMethod.GET)
	public StudentDetails getStudentById(@PathVariable Integer stuId) {
		return studentDetailsService.getStudentById(stuId);
	}
	
	@RequestMapping(value="/message", method=RequestMethod.GET)
	public String getMessage(){
		return configurationDetails.getName()+" \t"+configurationDetails.getUrl();
	}
}

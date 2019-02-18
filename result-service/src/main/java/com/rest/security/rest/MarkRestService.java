package com.rest.security.rest;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.security.model.Mark;
import com.rest.security.service.MarkService;

@RestController
public class MarkRestService {
	private MarkService markService;
	public MarkRestService(MarkService markService){
		this.markService = markService;
	}
	
	@RequestMapping(value="/mark", method=RequestMethod.GET)
	public @ResponseBody List<Mark> getAllMark(){
		return markService.getAllMark();
	}
	
	@RequestMapping(value="/mark/{stuId}", method=RequestMethod.GET)
	public Mark getMarkById(@PathVariable Integer stuId){
		return markService.getMarkById(stuId);
	}
}

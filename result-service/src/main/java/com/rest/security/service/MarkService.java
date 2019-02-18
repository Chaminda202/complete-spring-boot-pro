package com.rest.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rest.security.model.Mark;

@Service
public class MarkService {
	private final Map<Integer, Mark> marklist = new HashMap<>();

	{
		marklist.put(1, new Mark(1, "A+", 95, 1));
		marklist.put(2, new Mark(2, "B", 67, 12));
		marklist.put(3, new Mark(3, "A", 85, 7));
		marklist.put(4, new Mark(4, "B+", 73, 10));
		marklist.put(5, new Mark(5, "C", 52, 35));
	}

	public List<Mark> getAllMark() {
		return marklist.values().stream().collect(Collectors.toList());
	}

	@HystrixCommand(fallbackMethod = "getDefaultMarkById", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"), 
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "5")
			})
	public Mark getMarkById(Integer stuId) {
		Mark mark = marklist.get(stuId);
		if (mark != null)
			return marklist.get(stuId);
		else
			throw new RuntimeException("Mark Record Not found :- " + stuId);
	}

	@SuppressWarnings("unused")
	private Mark getDefaultMarkById(Integer stuId) {
		return new Mark(0, "F", 0, 0);
	}
}

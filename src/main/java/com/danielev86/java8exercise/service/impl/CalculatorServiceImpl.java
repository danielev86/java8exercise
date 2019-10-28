package com.danielev86.java8exercise.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.danielev86.java8exercise.service.CalculatorService;

@Service("calculatorService")
public class CalculatorServiceImpl implements CalculatorService {
	
	private IntFunction<Integer> doubleUp = x -> x * 2;
	
	private Function<Integer,Integer> multipleThree = x -> x*3;
	
	private Function<Integer,Double> divideTwo = x -> (double) x/2;

	public List<Integer> doubleUpAllElements() {
		List<Integer> results = new ArrayList<Integer>();
		getMockList().forEach(x -> results.add(doubleUp.apply(x)));
		return results;
	}
	
	public List<Double> combineOperation(){
		List<Double> elements = new ArrayList<Double>();
		getMockList().forEach(x -> elements.add(multipleThree.andThen(divideTwo).apply(x)));
		return elements;
	}
	
	public List<Integer> getFilteredElements(){
		return getMockList().stream().filter(x -> (x<=30) && (x>=10)).collect(Collectors.toList());
	}

	private List<Integer> getMockList() {
		List<Integer> elements = new ArrayList<Integer>();
		elements.add(5);
		elements.add(20);
		elements.add(50);
		elements.add(100);
		return elements;
	}

}

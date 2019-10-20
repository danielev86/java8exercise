package com.danielev86.java8exercise.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.danielev86.java8exercise.utility.GenericUtility;

public class CommonService {
	
	@Autowired
	private GenericUtility genericUtility;

	public GenericUtility getGenericUtility() {
		return genericUtility;
	}

	public void setGenericUtility(GenericUtility genericUtility) {
		this.genericUtility = genericUtility;
	}

}

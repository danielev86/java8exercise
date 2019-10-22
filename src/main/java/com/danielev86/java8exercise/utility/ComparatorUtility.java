package com.danielev86.java8exercise.utility;

import static java.util.Collections.sort;

import java.util.List;

import com.danielev86.java8exercise.bean.PersonBean;

public interface ComparatorUtility {
	
	public static void getAllOrderedPersons(List<PersonBean> persons){
		sort(persons, (p1,p2)->{
			return p1.getFirstName().compareTo(p2.getLastName()) == 0
					? p1.getLastName().compareTo(p2.getLastName())
					: p1.getFirstName().compareTo(p2.getFirstName());
		});
	}

}

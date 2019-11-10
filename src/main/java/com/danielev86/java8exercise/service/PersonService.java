package com.danielev86.java8exercise.service;

import java.util.Date;

public interface PersonService {
	
	void getAllPersonsDetails();
	
	void getAllPersonBySpecificGender(String gender);

	void getAllOrderedPersonDetails();

	void getOrderedPersons();

	void getAllPersonsFilteredByDate(final Date from, Date to);

	void getOnlyNPersons(long limit);

	void getFirstPersonFIlteredByDate(Date from, Date to);

	void getAllPersonBySpecificGenderWithFilter(String gender);

	void getCyclistWithoutDuplicateElement();

	void getPersonsByGender();

	void getCyclistsByTeam();

}

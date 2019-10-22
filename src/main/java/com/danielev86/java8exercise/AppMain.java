package com.danielev86.java8exercise;

import static com.danielev86.java8exercise.constats.IConstants.FEMALE_GENDER_CODE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.danielev86.java8exercise.service.PersonService;
import com.danielev86.java8exercise.service.impl.PersonServiceImpl;

@ComponentScan(basePackages = "com.danielev86")
public class AppMain {
	
	private static final Logger logger = LoggerFactory.getLogger(AppMain.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(AppMain.class);
		PersonService personService = ctx.getBean(PersonServiceImpl.class);
		personService.getAllPersonsDetails();
		personService.getAllPersonBySpecificGender(FEMALE_GENDER_CODE);
		personService.getAllOrderedPersonDetails();
		personService.getOrderedPersons();
	}

}

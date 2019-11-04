package com.danielev86.java8exercise;

import static com.danielev86.java8exercise.constats.IConstants.FEMALE_GENDER_CODE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.danielev86.java8exercise.service.CalculatorService;
import com.danielev86.java8exercise.service.PersonService;
import com.danielev86.java8exercise.service.impl.CalculatorServiceImpl;
import com.danielev86.java8exercise.service.impl.PersonServiceImpl;

@ComponentScan(basePackages = "com.danielev86")
public class AppMain {
	
	private static String PATTERN_DAY = "dd/MM/yyyy";
	
	public static void main(String[] args) throws ParseException {
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(AppMain.class);
		PersonService personService = ctx.getBean(PersonServiceImpl.class);
		personService.getAllPersonsDetails();
		personService.getAllPersonBySpecificGender(FEMALE_GENDER_CODE);
		personService.getAllOrderedPersonDetails();
		personService.getOrderedPersons();
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DAY);
		Date from = sdf.parse("01/01/1980");
		Date to = sdf.parse("01/01/2000");
		personService.getAllPersonsFilteredByDate(from, to);
		personService.getOnlyNPersons(10);
		personService.getFirstPersonFIlteredByDate(from, to);
		personService.getAllPersonBySpecificGenderWithFilter(FEMALE_GENDER_CODE);
		
		CalculatorService calculatorService = ctx.getBean(CalculatorServiceImpl.class);
		System.out.println(calculatorService.doubleUpAllElements());
		System.out.println(calculatorService.combineOperation());
		System.out.println(calculatorService.getFilteredElements());
		
	}

}

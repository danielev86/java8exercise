package com.danielev86.java8exercise;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.danielev86.java8exercise.service.PersonService;
import com.danielev86.java8exercise.service.impl.PersonServiceImpl;

@ComponentScan(basePackages = "com.danielev86")
public class AppMain {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppMain.class);
		PersonService personService = ctx.getBean(PersonServiceImpl.class);
		personService.getAllPersonsDetails().forEach(x -> System.out.println(x));
	}

}

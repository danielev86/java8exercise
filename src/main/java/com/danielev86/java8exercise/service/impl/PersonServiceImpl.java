package com.danielev86.java8exercise.service.impl;

import static com.danielev86.java8exercise.utility.ComparatorUtility.getAllOrderedPersons;
import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.danielev86.java8exercise.bean.PersonBean;
import com.danielev86.java8exercise.constats.IConstants;
import com.danielev86.java8exercise.service.CommonService;
import com.danielev86.java8exercise.service.PersonService;

@Service
public class PersonServiceImpl extends CommonService implements PersonService {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);


	
	public void getAllPersonsDetails(){
		writeCsvFile(getAllPersonFromCsv(), getGenericUtility().pathByOs() + "persons_all.csv");
	}
	
	@Override
	public void getAllOrderedPersonDetails() {
		//Build a comparator with java 8 function
		Comparator<PersonBean> comparator = Comparator.comparing(PersonBean::getFirstName)
				.thenComparing(Comparator.comparing(PersonBean::getLastName));
		List<PersonBean> persons = getAllPersonFromCsv();
		Collections.sort(persons, comparator);
		writeCsvFile(persons, getGenericUtility().pathByOs() + "persons_all_ordered.csv");
	}
	
	@Override
	public void getOrderedPersons() {
		List<PersonBean> persons = getAllPersonFromCsv();
		getAllOrderedPersons(persons);
		writeCsvFile(persons, getGenericUtility().pathByOs() + "persons_all_ordered_by_utility.csv");
	}
	
	public void getAllPersonBySpecificGender(String gender){
		List<PersonBean> persons = getAllPersonFromCsv();
		Map<String, List<PersonBean>> mapResult = persons.stream()
				.collect(groupingBy(PersonBean::getGender));
		writeCsvFile(mapResult.get(IConstants.FEMALE_GENDER_CODE), getGenericUtility().pathByOs() + "persons_gender.csv");
	}
	
	@Override
	public void getAllPersonBySpecificGenderWithFilter(String gender) {
		List<PersonBean> persons = getAllPersonFromCsv()
				.stream()
				.filter(p -> IConstants.FEMALE_GENDER_CODE.equals(gender))
				.collect(toList());
		writeCsvFile(persons, getGenericUtility().pathByOs() + "person_common_gender_filter.csv");
	
	}
	
	@Override
	public void getAllPersonsFilteredByDate(Date from, Date to) {
		List<PersonBean> persons = getAllPersonFromCsv();
		List<PersonBean> personsResult = persons
		.stream()
		.filter(person -> (person.getBorn().after(from)) && (person.getBorn().before(to)) )
		.collect(Collectors.toList());
		Comparator<PersonBean> comparatorOrderDay = Comparator.comparing(PersonBean::getBorn)
				.thenComparing(PersonBean::getFirstName)
				.thenComparing(PersonBean::getLastName);
		Collections.sort(personsResult, comparatorOrderDay);
		writeCsvFile(personsResult, getGenericUtility().pathByOs() + "persons_filtered_by_date.csv");
	}
	
	@Override
	public void getOnlyNPersons(long limit) {
		List<PersonBean> persons = getAllPersonFromCsv()
				.stream()
				.filter(person -> IConstants.FEMALE_GENDER_CODE.equals(person.getGender()))
				.limit(limit)
				.collect(toList());
		writeCsvFile(persons, getGenericUtility().pathByOs() + "limit_" + limit + "n_persons.csv" );
	}
	
	@Override
	public void getFirstPersonFIlteredByDate(Date from, Date to) {
		PersonBean person = getAllPersonFromCsv()
				.stream()
				.filter(p -> p.getBorn().after(from) && p.getBorn().before(to))
				.findFirst()
				.get();
		if (person != null) {
			logger.info("There is one element which fulfills this condition. Person: " + person);
		}else {
			logger.error("There are not elements which fulfill this condition ");
		}
	}
	
	@Override
	public void getCyclistWithoutDuplicateElement() {
		getGenericUtility().getAllCyclistsMockData()
		.stream()
		.distinct()
		.forEach(c -> logger.info("Cyclist info: " + c));
	}
	
	private List<PersonBean> getAllPersonFromCsv() {
		logger.info("START parsing csv file with details of individual person");
		List<PersonBean> persons = new ArrayList<PersonBean>();
		File file = new File(getClass().getClassLoader().getResource(IConstants.PERSON_CSV_FILE_NAME).getFile());
		List<String> records = getGenericUtility().parseCsvFile(file, TRUE);
		if (CollectionUtils.isNotEmpty(records)) {
			records.forEach(record ->{
				String[] splittedRecord = record.split(",");
				PersonBean person = new PersonBean();
				person.setId(Long.valueOf(splittedRecord[0]));
				person.setFirstName(splittedRecord[1]);
				person.setLastName(splittedRecord[2]);
				person.setEmail(splittedRecord[3]);
				person.setBorn(getGenericUtility().parseDate(splittedRecord[4]));
				person.setGender(splittedRecord[5].toUpperCase());
				person.setIpAddress(splittedRecord[6]);
				persons.add(person);
			});
		}
		logger.info("END parsing csv file with details of individual person");
		return persons;
	}
	
	private void writeCsvFile(List<PersonBean> persons, String fileName) {
		getGenericUtility().writeCsvFile(getCsvHeader(), getRecords(persons), fileName);
	}
	
	private String[] getCsvHeader() {
		String[] headers = {"FIRST_NAME", "LAST_NAME", "EMAIL", "BORN", "GENDER", "IP_ADRESS" };
		return headers;
	}
	
	private List<List<String>> getRecords(List<PersonBean> lstPerson){
		List<List<String>> records = new ArrayList<List<String>>();
		lstPerson.forEach(person ->{
			List<String> record = new ArrayList<String>();
			record.add( person.getFirstName());
			record.add( person.getLastName());
			record.add( person.getEmail());
			record.add( getGenericUtility().formatDate(person.getBorn()));
			record.add( person.getGender());
			record.add( person.getIpAddress());
			records.add(record);
		});
		return records;
	}

}

package com.danielev86.java8exercise.service.impl;

import static com.danielev86.java8exercise.utility.ComparatorUtility.getAllOrderedPersons;
import static java.lang.Boolean.TRUE;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
import com.danielev86.java8exercise.utility.ComparatorUtility;

@Service
public class PersonServiceImpl extends CommonService implements PersonService {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

	
	public void getAllPersonsDetails(){
		writeCsvFile(getAllPersonFromCsv(), "d:\\persons_all.csv");
	}
	
	@Override
	public void getAllOrderedPersonDetails() {
		List<PersonBean> persons = getAllPersonFromCsv();
		Collections.sort(persons, (p1, p2) ->{
			return p1.getFirstName().compareTo(p2.getFirstName()) == 0
					? p1.getLastName().compareTo(p2.getLastName())
					: p1.getFirstName().compareTo(p2.getFirstName());
		});
		writeCsvFile(persons, "d:\\persons_all_ordered.csv");
	}
	
	@Override
	public void getOrderedPersons() {
		List<PersonBean> persons = getAllPersonFromCsv();
		getAllOrderedPersons(persons);
		writeCsvFile(persons, "d:\\persons_all_ordered_by_utility.csv");
	}
	
	public void getAllPersonBySpecificGender(String gender){
		List<PersonBean> persons = getAllPersonFromCsv();
		Map<String, List<PersonBean>> mapResult = persons.stream()
				.collect(Collectors.groupingBy(PersonBean::getGender));
		writeCsvFile(mapResult.get(IConstants.FEMALE_GENDER_CODE), "d:\\persons_gender.csv");
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

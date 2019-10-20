package com.danielev86.java8exercise.service.impl;

import static java.lang.Boolean.TRUE;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

	
	public List<PersonBean> getAllPersonsDetails(){
		return getAllPersonFromCsv();
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

}

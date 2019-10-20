package com.danielev86.java8exercise.utility;

import static com.danielev86.java8exercise.constats.IConstants.DAY_PATTERN;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GenericUtility {
	
	private static final Logger logger = LoggerFactory.getLogger(GenericUtility.class);
	
	public List<String> parseCsvFile(File file, boolean isHeaderPresent){
		List<String> records = new ArrayList<String>();
		CSVParser csvParser = null;
		try {
			csvParser = isHeaderPresent
					? CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new InputStreamReader(new FileInputStream(file)))
					: CSVFormat.DEFAULT.parse(new InputStreamReader(new FileInputStream(file)));
			csvParser.forEach(record -> {
				Iterator<String> iterRecord = record.iterator();
				List<String> recordValues = new ArrayList<String>();
				while (iterRecord.hasNext()) {
					recordValues.add(iterRecord.next());
				}
				records.add(StringUtils.join(recordValues, ","));
			});
		} catch (IOException e) {
			logger.error("Error parsing from csv file FILENAME: " + file.getName(), e);
		}finally {
			if (csvParser != null) {
				try {
					csvParser.close();
				} catch (IOException e) {
					logger.error("Error closing csv file FILENAME: " + file.getName(), e);
				}
			}
		}
		return records;
	}

	public Date parseDate(String date) {
		Date dateParsed = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(DAY_PATTERN);
		try {
			dateParsed = sdf.parse(date);
		} catch (ParseException e) {
			logger.error("Error in parsing date", e);
		}
		
		return dateParsed;

	}
	
}

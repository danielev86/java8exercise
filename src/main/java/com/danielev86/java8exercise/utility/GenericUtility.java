package com.danielev86.java8exercise.utility;

import static com.danielev86.java8exercise.constats.IConstants.DAY_PATTERN;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.danielev86.java8exercise.bean.CyclistBean;

@Component
public class GenericUtility {
	
	private static final Logger logger = LoggerFactory.getLogger(GenericUtility.class);
	
	private String OS = System.getProperty("os.name").toLowerCase();
	
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
	
	public <T> void writeCsvFile(String[] headers, List<List<String>> records, String fileName) {
		
		if (CollectionUtils.isNotEmpty(records)) {
			BufferedWriter writer = null;
			CSVPrinter csvPrinter = null;

			try {
				writer = Files.newBufferedWriter(Paths.get(fileName));
				
				csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers));
				
				for (List<String> record : records) {
					csvPrinter.printRecord(record);
				}

			} catch (Exception e) {
				logger.error("ERROR Writing csv file", e);;
			}finally {
				try {
					if (writer != null) {				
						writer.close();
					}
					if (csvPrinter != null) {
						csvPrinter.close();
					}
				} catch (IOException e) {
					logger.error("Error closing file", e);
				}
			}
			
		}
	
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
	
	public String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DAY_PATTERN);
		return sdf.format(date);
	}
	
	public String pathByOs() {
		if (OS.contains("windows")) {
			return "d:\\";
		}else {
			return "/tmp/";
		}
	}
	
	public List<CyclistBean> getAllCyclistsMockData(){
		CyclistBean c1 = new CyclistBean();
		c1.setFirstName("Vincenzo");
		c1.setLastName("Nibali");
		c1.setTeamName("Trek - Segafredo");
		CyclistBean c2 = new CyclistBean();
		c2.setFirstName("Christofer");
		c2.setLastName("Froome");
		c2.setTeamName("Team - Ineos");
		CyclistBean c3 = new CyclistBean();
		c3.setFirstName("Tom");
		c3.setLastName("Dumoulin");
		c3.setTeamName("Jumbo VIsma");
		CyclistBean c4 = new CyclistBean();
		c4.setFirstName("Gianluca");
		c4.setLastName("Brambilla");
		c4.setTeamName("Trek - Segafredo");
		CyclistBean c5 = new CyclistBean();
		c5.setFirstName("Gianluca");
		c5.setLastName("Brambilla");
		c5.setTeamName("Trek - Segafredo");
		
		List<CyclistBean> cyclists = new ArrayList<CyclistBean>();
		cyclists.add(c1);
		cyclists.add(c2);
		cyclists.add(c3);
		cyclists.add(c4);
		cyclists.add(c5);
		
		return cyclists;
	}
	
}

package seedu.dailyplanner.logic.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

import seedu.dailyplanner.commons.util.DateUtil;

public class nattyParser {
	//@@author A0140124B
	private com.joestelmach.natty.Parser nattyParserPackage;

	public nattyParser() {
		nattyParserPackage = new com.joestelmach.natty.Parser();
	}

	public String parse(String dateAndTime) {
		List<DateGroup> groups = nattyParserPackage.parse(dateAndTime);
		Date parsedDateAndTime = new Date();
		for (DateGroup group : groups) {
			parsedDateAndTime = group.getDates().get(0);
			break;

		}
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh.mma");
		return df.format(parsedDateAndTime);
	}

	public String parseDate(String date) {
	   
	    if (DateUtil.isValidDayMonthAnd4DigitYearFormat(date)) {
            if (date.charAt(2) != '/') {
                return "0"+date;
            } else {
                return date;
            }
       }
	    
	    if (DateUtil.isValidDayMonthAnd2DigitYearFormat(date)) {
	        String dateWithAddedZero;
            if (date.charAt(2) != '/') {
                dateWithAddedZero =  "0"+date;
            } else {
                dateWithAddedZero = date;
            }
            dateWithAddedZero = DateUtil.convertTo4DigitYearFormat(dateWithAddedZero);
            
            return dateWithAddedZero;
       }
	    
		List<DateGroup> groups = nattyParserPackage.parse(date);
		Date parsedDate = new Date();
		for (DateGroup group : groups) {
			parsedDate = group.getDates().get(0);
			break;
		}
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(parsedDate);
	}

	public String parseTime(String time) {
		List<DateGroup> groups = nattyParserPackage.parse(time);
		Date parsedTime = new Date();
		for (DateGroup group : groups) {
			parsedTime = group.getDates().get(0);
			break;

		}
		DateFormat df = new SimpleDateFormat("hh.mma");
		return df.format(parsedTime);
	}

}
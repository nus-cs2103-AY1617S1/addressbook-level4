package seedu.dailyplanner.logic.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

public class nattyParser {

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
		DateFormat df = new SimpleDateFormat("ddMMyyyy hh.mma");
		return df.format(parsedDateAndTime);
	}

	public String parseDate(String date) {
		List<DateGroup> groups = nattyParserPackage.parse(date);
		Date parsedDate = new Date();
		for (DateGroup group : groups) {
			parsedDate = group.getDates().get(0);
			break;
		}
		DateFormat df = new SimpleDateFormat("ddMMyyyy");
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
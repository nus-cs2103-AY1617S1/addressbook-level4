package seedu.dailyplanner.logic.parser;

import java.text.SimpleDateFormat;

import seedu.dailyplanner.model.task.Date;
import seedu.dailyplanner.model.task.DateTime;
import seedu.dailyplanner.model.task.Time;

public class DateTimeParser {

  
    /* Guarantees that there is both date and time */
    // Given in ddmmyyyy hh:mm
    public DateTime parseDateAndTime(String dateTime) {
	String[] dateTimeArray = dateTime.split(" ");
	String time = dateTimeArray[1];
	Time formattedTime = new Time(time);
	
	String date = dateTimeArray[0];
	Date formattedDate;
	int day = Integer.parseInt(date.substring(0, 2));
	int month = Integer.parseInt(date.substring(2, 4));
	int year = Integer.parseInt(date.substring(4));
	java.util.Calendar cal = java.util.Calendar.getInstance();
	cal.set(year + 1900, month, day);
	java.util.Date dateThis = cal.getTime();
	java.util.Date dateToday = new java.util.Date();
	SimpleDateFormat df = new SimpleDateFormat("EEEEE");
	if (isSameDay(dateToday, dateThis)) {
	    Da
	} else if (isYesterday(dateToday, dateThis)) {
	    formattedDate = new Date("Yesterday");
	} else if (isTomorrow(dateToday, dateThis)) {
	    formattedString += "Tomorrow, ";
	} else if (isLastWeek(dateToday, dateThis)) {
	    formattedString += "Last " + df.format(dateThis) + ", ";
	} else if (isNextWeek(dateToday, formattedDate)) {
	    formattedString += "Next " + df.format(dateThis) + ", ";
	} else {
	    
	}
	return new DateTime(formattedDate, formattedTime);
    }

    public DateTime parseDate(String start) {
	// TODO Auto-generated method stub
	return null;
    }

    public boolean containsDate(String endString) {
	// TODO Auto-generated method stub
	return false;
    }

    public String retrieveStart(String start) {
	// TODO Auto-generated method stub
	return null;
    }

}

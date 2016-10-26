package seedu.cmdo.commons.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.cmdo.model.tag.Tag;
import seedu.cmdo.model.tag.UniqueTagList;
import seedu.cmdo.model.task.DueByDate;
import seedu.cmdo.model.task.DueByTime;

public class SearchUtil {
    public static boolean containsIgnoreCase(String source, String query) {
        String[] split = source.toLowerCase().split("\\s+");
        List<String> strings = Arrays.asList(split);
        return strings.stream().filter(s -> s.contains(query.toLowerCase())).count() > 0;
    }
    
    // For searching of tags.
    // @@author A0139661Y
    public static boolean containsIgnoreCase(UniqueTagList source, String query) {
    	ArrayList<String> strings = new ArrayList<String>(); 
    	for (Tag t : source) {
    		strings.add(t.tagName);
        }    	
        return strings.stream().filter(s -> s.contains(query.toLowerCase())).count() > 0;
    }

    /**
     * Queries the task in question for date and time based on the user's NLP input.
     * 
     * @param dbd
     * @param dbt
     * @param query
     * @return boolean
     * 
     * @@author A0139661Y
     */
    public static boolean containsTimeAndDate(DueByDate dbd, DueByTime dbt, String query) {
    	Parser parser = new Parser();
    	List<DateGroup> dtl = parser.parse(query);
    	ArrayList<LocalDate> userDates = new ArrayList<LocalDate>();
    	ArrayList<LocalTime> userTimes = new ArrayList<LocalTime>();
    	for (DateGroup dg : dtl) {
    		List<Date> dl = dg.getDates();
    		for (Date d : dl) {
    			LocalDate ld = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    			LocalTime lt = d.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    			userDates.add(ld);
    			userTimes.add(lt);
    		}
    	}
    	// User time will never be empty so there is no time
    	if (userTimes.isEmpty()) return false;
    	ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
    	ArrayList<LocalTime> times = new ArrayList<LocalTime>(); 
    	if (dbt.isRange()) {
    		times.add(dbt.end);
    	} if (!dbt.isFloating()) {
    		times.add(dbt.start);
    	} if (dbd.isRange()) {
    		dates.add(dbd.end);
    	} if (!dbd.isFloating()) {
    		dates.add(dbd.start);
    	}
    	if (dates.contains(LocalDate.now())) {
    		return times.stream().filter(s -> userTimes.contains(s)).count() > 0;
    	}
    	return times.stream().filter(s -> userTimes.contains(s)).count() > 0 
    			|| dates.stream().filter(s -> userDates.contains(s)).count() > 0; 
    }
}

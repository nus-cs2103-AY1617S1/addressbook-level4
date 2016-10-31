package seedu.cmdo.commons.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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
	public static int levenshtein_tolerance = 60;
	private static final int LEVENSHTEIN_FULL = 100;
	
	//@@author A0139661Y-unused
	public static void setLTolerance(int newTolerance) {
		levenshtein_tolerance = newTolerance;
	}
	
	// For searching of all parameters
	//@@author A0139661Y
    public static boolean containsIgnoreCase(String source, String query) {
        String[] split = source.toLowerCase().split("\\s+");
        List<String> strings = Arrays.asList(split);
        return strings.stream().filter(s -> s.equals(query.toLowerCase())
        		|| checkStringSimilarity(s, query) >= levenshtein_tolerance)
        		.count() > 0;
    }
    
    // For searching of tags.
    // @@author A0139661Y
    public static boolean containsIgnoreCase(UniqueTagList source, String query) {
    	ArrayList<String> strings = new ArrayList<String>(); 
    	for (Tag t : source) {
    		strings.add(t.tagName);
        }    	
        return strings.stream().filter(s -> s.equals(query.toLowerCase())
        		|| checkStringSimilarity(s, query) >= levenshtein_tolerance)
        		.count() > 0;
    }
    
	/**
	 * String similarity checking and returns a percentage of
	 * how similar the strings are
	 * 
	 * @param s1 - First string to check with.
	 * @param s2 - Secnd string to check with.
	 * @return double The percentage of the similarity of strings
	 * 
	 * @@author A0112898U-reused
	 */
    public static double checkStringSimilarity(String s1, String s2) {
    	// s1 should always be bigger, for easy check thus the swapping.
    	if (s2.length() > s1.length()) {
            String tempStr = s1; 
            s1 = s2; 
            s2 = tempStr;
        }
    	
        int bigLen = s1.length();
        
        if (bigLen == 0) { 
        	return LEVENSHTEIN_FULL; 
        }
        return ((bigLen - extractLDistance(s1, s2)) /
        		(double) bigLen) * LEVENSHTEIN_FULL;
    }
    
    /**
     * Computes the distance btw the 2 strings, via the Levenshtein Distance Algorithm
     * 
	 * @param s1 - First string to check with.
	 * @param s2 - Second string to check with.
     * @return the new cost to change to make the string same
     * 
     * @@author A0112898U-reused
     */
    private static int extractLDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int[] costToChange = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                	costToChange[j] = j;
                }  else {
                    if (j > 0) {
                        int newValue = costToChange[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue),
                            		costToChange[j]) + 1;
                        }
                        costToChange[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) {
            	costToChange[s2.length()] = lastValue;
            }
        }
        return costToChange[s2.length()];
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

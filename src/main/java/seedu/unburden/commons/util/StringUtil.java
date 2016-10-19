package seedu.unburden.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.tag.UniqueTagList;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    public static boolean containsIgnoreCase(String source, String query) {
        String[] split = source.toLowerCase().split("\\s+");
        List<String> strings = Arrays.asList(split);
        return strings.stream().filter(s -> s.equals(query.toLowerCase())).count() > 0;
    }
    
    /*public static boolean tagsContainsIgnoreCase(UniqueTagList source, String query) {
    	boolean truefalse = false;
    	ObservableList<Tag> list = source.getInternalList();
    	String[] split;
    	List<String> strings = null;
    	for(Tag tag: list){
    		split = tag.tagName.toLowerCase().split("\\s+");
    		strings = Arrays.asList(split);
    		truefalse = truefalse || strings.stream().filter(s -> s.equals(query.toLowerCase())).count() > 0;
    	}
        return truefalse;
    }*/
    
    
    public static boolean containsDate(String source, String query){
    	List<String> strings = new ArrayList<String>(Arrays.asList(source));
    	return strings.stream().filter(s -> s.equals(query)).count() > 0;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t){
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     *   Will return false for null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s){
        return s != null && s.matches("^0*[1-9]\\d*$");
    }
}

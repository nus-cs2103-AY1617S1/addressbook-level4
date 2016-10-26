//@@author A0139916U
package seedu.savvytasker.logic.parser;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.joestelmach.natty.DateGroup;

public class DateParser {
    /**
     * Represents a date-time that may be partially inferred on its
     * date component or time component. If both components are not 
     * inferred, then the date-time is exactly as the user has specified
     * it to be.
     */
    public class InferredDate {
        private final Date inferredDateTime;
        private final boolean dateInferred;
        private final boolean timeInferred;
        
        public InferredDate(Date inferredDateTime, boolean dateInferred, boolean timeInferred) {
            this.inferredDateTime = inferredDateTime;
            this.dateInferred = dateInferred;
            this.timeInferred = timeInferred;
        }
        
        /**
         * Gets the inferred date-time.
         * @return the inferred date-time
         */
        public Date getInferredDateTime() {
            return this.inferredDateTime;
        }
        
        /**
         * Checks if the date component is inferred
         * @return true if inferred, false otherwise
         */
        public boolean isDateInferred() {
            return this.dateInferred;
        }
        
        /**
         * Checks if the time component is inferred
         * @return true if inferred, false otherwise
         */
        public boolean isTimeInferred() {
            return this.timeInferred;
        }
    }
    
    private com.joestelmach.natty.Parser nattyParser;
    
    public DateParser() {
        this.nattyParser = new com.joestelmach.natty.Parser();
    }
    
    /**
     * Parses an input for a single date. It is considered a parsing error 
     * if multiple dates are specified in the given input.
     * 
     * @param input the input to be parsed
     * @return an InferredDate object resulting from the parsing
     * @throws ParseException if the input contains multiple dates or no dates.
     */
    public InferredDate parseSingle(String input) throws ParseException {
        assert input != null;
        
        // Temporary workaround for natty's failure at adapting to locales dd-mm-yyyy, until
        // their issue is closed.
        if (!Locale.getDefault().equals(Locale.US)) {
            input = input.replaceAll("(\\d{1,2})-(\\d{1,2})-((?:\\d\\d){1,2})", "$2-$1-$3");
        }
        
        List<DateGroup> dateGroups = this.nattyParser.parse(input);
        int totalDates = countDates(dateGroups);
        
        if (totalDates == 0)
            throw new ParseException(input, "Failed to understand given date.");
        
        if (totalDates > 1)
            throw new ParseException(input, "Too many dates entered.");
        
        DateGroup group = dateGroups.get(0);
        
        
        
        return new InferredDate(
                group.getDates().get(0),
                group.isDateInferred(),
                group.isTimeInferred());
        
    }
    
    private int countDates(List<DateGroup> dateGroups) {
        int totalNumOfDates = 0;
        for(DateGroup group : dateGroups) {
            totalNumOfDates += group.getDates().size();
        }
        return totalNumOfDates;
    }
}

package seedu.forgetmenot.model.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;

//@@author A0139671X
/**
 * Represents a Task's recurrence in the task manager.
 */
public class Recurrence {

    public static final int DEFAULT_OCCURENCE = 10;
    public static final String INVALID_RECURRENCE_FORMAT = "Sorry! Your recurrence format was invalid! Please try again.";
    
    private static final Pattern RECURRENCE_DATA_ARGS_FORMAT = Pattern.compile("(?<freq>((\\d* )?(day|week|month|year)(s)?))"
            + "( (x|X)(?<occ>(\\d++)))?", Pattern.CASE_INSENSITIVE);

    private boolean value;
    public String days;
    public int occurences;
    
    public Recurrence (String args) throws IllegalValueException {
        final Matcher matcher = RECURRENCE_DATA_ARGS_FORMAT.matcher(args.trim());
        
        if (args.equals("")) {
            this.days = "";
            this.value = false;
            this.occurences = 0;
        }
        
        else if (!matcher.matches()) {
            throw new IllegalValueException(INVALID_RECURRENCE_FORMAT);
        }
        
        else {
            assignRecurrenceValues(args, matcher);
        }
    }

    
    public boolean getValue() {
        return this.value;
    }
    
    public String getRecurFreq() {
        return this.days;
    }
    
    public int getOccurence() {
        return this.occurences;
    }
    
    @Override
    public String toString() {
        return getAppropriateText();
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurrence // instanceof handles nulls
                        && this.days.equals(((Recurrence) other).days)); // state check
    }
    
    @Override
    public int hashCode() {
        return days.hashCode();
    }

    /**
     * Converts the recurrence duration to a more appropriate english word
     * @return String in the appropriate display format
     */
    public String getAppropriateText() {
        if (!value) {
            return "NIL";
        }
        
        else if (days.toLowerCase().equals("day")) {
            return "Daily";
        }
        
        else if (days.toLowerCase().equals("week")) {
            return "Weekly";
        }
        
        else if (days.toLowerCase().equals("month")) {
            return "Monthly";
        }
        
        else if (days.toLowerCase().equals("year")) {
            return "Yearly";
        }
        
        else {
            return days;
        }
    }
    
    /**
     * Assigns the recurrence attributes to follow either a default occurence or a assigned occurence by the user 
     */
    private void assignRecurrenceValues(String recurrenceString, final Matcher matcher) {
        
        if (!recurrenceString.contains("x")) {
            this.occurences = DEFAULT_OCCURENCE;
        }
        
        else {
            this.occurences = Integer.parseInt(matcher.group("occ").trim());
        }

        this.days = matcher.group("freq");
        this.value = true;
    }

}

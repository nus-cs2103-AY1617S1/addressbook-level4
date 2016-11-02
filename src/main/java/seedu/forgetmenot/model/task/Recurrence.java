package seedu.forgetmenot.model.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's recurrence in the task manager.
 * @@author A0139671X
 */
public class Recurrence {

    public static final int DEFAULT_OCCURENCE = 10;
    
    private static final Pattern RECURRENCE_DATA_ARGS_FORMAT = Pattern.compile("(?<freq>((\\d* )?(day|week|month|year)(s)?))"
            + "( x(?<occ>(\\d++)))?", Pattern.CASE_INSENSITIVE);

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
            throw new IllegalValueException("Sorry! Your recurrence format was invalid! Please try again.");
        }
        
        else {
            assignRecurrenceValues(args, matcher);
        }
    }

    
    public boolean getValue() {
        return this.value;
    }
    
    public String getRecurFreq() {
        System.out.println(this.days);
        return this.days;
    }

    public void setRecurFreq(String days) {
        this.days = days;
    }
    
    public int getOccurence() {
        return this.occurences;
    }
    
    @Override
    public String toString() {
        if (!value)
            return "NIL";
        if (days.toLowerCase().equals("day"))
            return "Daily";
        if (days.toLowerCase().equals("week"))
            return "Weekly";
        if (days.toLowerCase().equals("month"))
            return "Monthly";
        if (days.toLowerCase().equals("year"))
            return "Yearly";
        
        return days;
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
     * Assigns the recurrence attributes to follow either a default occurence or a assigned occurence by the user 
     */
    private void assignRecurrenceValues(String recurrenceString, final Matcher matcher) {
        
        if(!recurrenceString.contains("x")) {
            this.occurences = DEFAULT_OCCURENCE;
        }
        else {
            this.occurences = Integer.parseInt(matcher.group("occ").trim());
        }

        this.days = matcher.group("freq");
        this.value = true;
    }

}

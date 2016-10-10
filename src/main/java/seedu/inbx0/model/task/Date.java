package seedu.inbx0.model.task;

import java.text.SimpleDateFormat;
import java.util.List;

import com.joestelmach.natty.Parser;

import seedu.inbx0.commons.exceptions.IllegalValueException;

/**
 * Represents Date of a Task Event in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */

public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date can either be a numeric string, alphanumeric string, or alphabet string";
    public static final String DATE_NUMERIC_VALIDATION_REGEX = "\\d+";
/*    public static final String DATE_ALPHANUMERIC_VALIDATION_REGEX = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";
    public static final String DATE_ALPHABETICAL_VALIDATION_REGEX = "[\\p{Alpha} ]+";
    public static final String SPLIT_NUM_AND_ALPHABET_REGEX = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";
    public static final int NUM_OF_STRINGS_IN_ALLOWED_DATE_EXPRESSIONS = 5;
    public static final int NUM_OF_STRINGS_IN_ALLOWED_MONTH_NAMES = 23;
    public static final int NUM_OF_STRINGS_IN_ALLOWED_DAY_NAMES = 14;
    public static final int NUM_OF_STRINGS_IN_ALLOWED_DATE_PREPOSITIONS = 2;
    public static final String [] ALLOWED_DATE_EXPRESSIONS = {"tomorrow", "today", 
                                                               "next week", "next month", "next year"};
    public static final String [] ALLOWED_DATE_PREPOSITIONS = {"this", "next"};
    public static final String [] ALLOWED_DAY_NAMES = {"mon", "monday",
                                                       "tue", "tuesday",
                                                       "wed", "wednesday",
                                                       "thu", "thursday",
                                                       "fri", "friday",
                                                       "sat", "saturday",
                                                       "sun", "sunday"                                                      
                                                       };
    public static final String [] ALLOWED_MONTH_NAMES = {"jan", "january",
                                                         "feb", "february",
                                                         "mar", "march",
                                                         "apr", "april",
                                                         "may",
                                                         "jun", "june",
                                                         "jul", "july",
                                                         "aug", "august",
                                                         "sep", "september",
                                                         "oct", "october",
                                                         "nov", "november",
                                                         "dec", "december"
                                                         };
                                                           
  */  
    public final String value;
    public final int day;
    public final int month;
    public final int year;
    
    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given start date string is invalid.
     */
    
    public Date(String date) throws IllegalValueException {
        assert date != null;
     //   date = date.trim();
        String tellDate = date;
     
        if(date != "" | date.length() != 0 | date != null) {
            List<java.util.Date> dates = new Parser().parse(tellDate.replaceAll("\\D+","")).get(0).getDates();             
            SimpleDateFormat ft = new SimpleDateFormat ("E, dd.MM.yyyy");
            tellDate = ft.format(dates.get(0));
            
            int digitsOnly = Integer.parseInt(tellDate.replaceAll("\\D+",""));
            this.day = digitsOnly / 1000000;
            this.month = (digitsOnly / 10000) % 100;
            this.year = digitsOnly % 10000;
            
        }
        else {
            this.day = 0;
            this.month = 0;
            this.year = 0;
        }
       
 /*       if (!isValidDate(date) &&  (date != "")) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }*/
        this.value = tellDate;
    }
    
    /**
     * Returns true if a given string is a valid task date.
     */
    public static boolean isValidDate(String test) {
        return (test.matches(DATE_NUMERIC_VALIDATION_REGEX) && (test.length() == 8));
    }
 /*       boolean numericCheck = false;
        boolean alphanumericCheck = false;
        boolean alphabeticalCheck = false;
        
        if(test.matches(DATE_NUMERIC_VALIDATION_REGEX) && (test.length() == 8)) {
            numericCheck = true;
        }
        
        if (test.matches(DATE_ALPHABETICAL_VALIDATION_REGEX)) {
            for (int i = 0; i < NUM_OF_STRINGS_IN_ALLOWED_DATE_EXPRESSIONS; i++) {
                
                if(test == ALLOWED_DATE_EXPRESSIONS[i])              
                    alphabeticalCheck = true;                
            }
   
            test = test.toLowerCase();
            String [] alphabeticalTestCheck = test.split(" ");
            
            if (alphabeticalTestCheck.length == 2) {
                boolean datePrepositionCheck = false;
                boolean dayNameCheck = false;
                for(int i = 0;  i < NUM_OF_STRINGS_IN_ALLOWED_DATE_PREPOSITIONS; i++) {
                    if(alphabeticalTestCheck[0] == ALLOWED_DATE_PREPOSITIONS[i])
                        datePrepositionCheck = true;
                }
                
                for(int i = 0; i < NUM_OF_STRINGS_IN_ALLOWED_DAY_NAMES; i++) {
                    if(alphabeticalTestCheck[1] == ALLOWED_DAY_NAMES[i])
                        dayNameCheck = true;
                }
                
                if(datePrepositionCheck == true && dayNameCheck == true)
                    alphabeticalCheck = true;
            }
            
        }
        
        if(test.matches(DATE_ALPHANUMERIC_VALIDATION_REGEX)) {
            test = test.replace(" ", "");
            test = test.toLowerCase();
            String [] alphanumericTestCheck = test.split(SPLIT_NUM_AND_ALPHABET_REGEX);
            
            if(alphanumericTestCheck.length == 2) {
                if(alphanumericTestCheck[0].matches("[a-zA-Z]+") && alphanumericTestCheck[1].matches("\\d+")) {
                    if(alphanumericTestCheck[1].length() == 1 | alphanumericTestCheck[1].length() == 2) {
                        for(int i = 0; i < NUM_OF_STRINGS_IN_ALLOWED_MONTH_NAMES; i++ ) {
                            if(alphanumericTestCheck[0] == ALLOWED_MONTH_NAMES[i])
                                alphanumericCheck = true;
                        }
                    }
                }
                
                if(alphanumericTestCheck[0].matches("\\d+") && alphanumericTestCheck[1].matches("[a-zA-Z]+")) {
                    if(alphanumericTestCheck[0].length() == 1 | alphanumericTestCheck[0].length() == 2) {
                        for(int i = 0; i < NUM_OF_STRINGS_IN_ALLOWED_MONTH_NAMES; i++ ) {
                            if(alphanumericTestCheck[1] == ALLOWED_MONTH_NAMES[i])
                                alphanumericCheck = true;
                        }
                    }
                }
            }
        } 
            
        return (numericCheck | alphanumericCheck | alphabeticalCheck);
    } */
    
    @Override
    public String toString() {      
        return value;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getDate() {
        return value;
    }
    
    public int getDay() {
        return day;
    }
    
    public int getMonth() {
        return month;
    }
    
    public int getYear() {
        return year;
    }
}

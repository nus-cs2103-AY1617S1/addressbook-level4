package seedu.inbx0.model.task;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.Parser;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.IncorrectCommand;

/**
 * Represents Date of a Task Event in the tasklist.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */

public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date can either be a numeric string, alphanumeric string, or alphabet string \n"
                                                        + "Example: DD/MM/YYYY format OR 3rd Jan OR next year";
    public static final Pattern DATE_NUMERIC_VALIDATION_REGEX = Pattern.compile("(?<front>[0-9]+)[./-](?<middle>[0-9]+)[./-](?<back>[0-9]+)");
    public static final String DATE_NUMERIC_VALIDATION_REGEX_2 = "\\d+";
/*    public static final String DATE_ALPHANUMERIC_VALIDATION_REGEX = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";
    public static final String DATE_ALPHABETICAL_VALIDATION_REGEX = "[\\p{Alpha} ]+";
    public static final String SPLIT_NUM_AND_ALPHABET_REGEX = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";
*/
    public final String value;
    public final int day;
    public final int month;
    public final int year;
    public final String dayWord;
    public final String DDMMYYYYFormat;
    
    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given start date string is invalid.
     */
    
    public Date(String date) throws IllegalValueException {
        assert date != null;
     //   date = date.trim();
        final Matcher matcher = DATE_NUMERIC_VALIDATION_REGEX.matcher(date.trim());
             
        if(date == "" | date.length() == 0 | date == null) {
            this.day = 0;
            this.month = 0;
            this.year = 0;
            this.value = "";
            this.dayWord = "";
            this.DDMMYYYYFormat = "";            
        }
        else if(date.matches(DATE_NUMERIC_VALIDATION_REGEX_2)) {
            
            int numberDate = Integer.parseInt(date);
            
            if(date.length() == 8) {
            this.day = numberDate / 1000000;
            this.month = (numberDate / 10000) % 100;
            this.year = numberDate % 10000;

            String nattyFormat = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
            List<java.util.Date> dates = new Parser().parse(nattyFormat).get(0).getDates();
            SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
            SimpleDateFormat dayInWord = new SimpleDateFormat ("E, ");
            
            this.dayWord = dayInWord.format(dates.get(0));
            this.DDMMYYYYFormat = ft.format(dates.get(0));
            this.value = ft.format(dates.get(0)).replaceAll("\\D+","");
            }
            else
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        else if(matcher.matches()) {
            
            String dateFront = matcher.group("front");
            String dateMiddle = matcher.group("middle");
            String dateBack = matcher.group("back");
            
            if(dateFront.length() == 4) {
                this.year = Integer.parseInt(dateFront);
                this.month = Integer.parseInt(dateMiddle); 
                this.day = Integer.parseInt(dateBack);
            }
            else if(dateFront.length() <= 0 | dateFront.length() > 4 | dateMiddle.length() <= 0 |
                    dateMiddle.length() > 2 | dateBack.length() < 2 | dateBack.length() > 4)
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            else {
                this.day = Integer.parseInt(dateFront);
                this.month = Integer.parseInt(dateMiddle); 
                this.year = Integer.parseInt(dateBack);
            }
            
            String nattyFormat = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
            List<java.util.Date> dates = new Parser().parse(nattyFormat).get(0).getDates();
            SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
            SimpleDateFormat dayInWord = new SimpleDateFormat ("E, ");
            
            this.dayWord = dayInWord.format(dates.get(0));
            this.DDMMYYYYFormat = ft.format(dates.get(0));
            
            this.value = ft.format(dates.get(0)).replaceAll("\\D+","");
            }
        else {
            try {
            List<java.util.Date> dates = new Parser().parse(date).get(0).getDates(); 
            
            SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
            SimpleDateFormat dayInWord = new SimpleDateFormat ("E, ");
            SimpleDateFormat numericFormat = new SimpleDateFormat ("ddMMyyyy");
            this.DDMMYYYYFormat = ft.format(dates.get(0));
            this.value = numericFormat.format(dates.get(0));
            int digitsOnly = Integer.parseInt(DDMMYYYYFormat.replaceAll("\\D+",""));
            this.day = digitsOnly / 1000000;
            this.month = (digitsOnly / 10000) % 100;
            this.year = digitsOnly % 10000;
            this.dayWord = dayInWord.format(dates.get(0));  

            } catch (IndexOutOfBoundsException e) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }
            
        }
        
       
 /*       if (!isValidDate(date) &&  (date != "")) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }*/
        
    }
    
    /**
     * Returns true if a given string is a valid task date.
     */
//    public static boolean isValidDate(String test) {
//        return (test.matches(DATE_NUMERIC_VALIDATION_REGEX) && (test.length() == 8));
//    }
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
    
    public String getTotalDate() {
        return dayWord + DDMMYYYYFormat;
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

package seedu.inbx0.model.task;

import java.util.Arrays;

import seedu.inbx0.commons.exceptions.IllegalValueException;

/**
 * Represents Importance of a Task in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidImportance(String)}
 */

public class Importance {
    
    public static final String MESSAGE_IMPORTANCE_CONSTRAINTS = "Importance has to be alphabetical format";
    public static final String IMPORTANCE_VALIDATION_REGEX = "[a-zA-Z]+";
    public static final int NUM_OF_STRINGS_IN_ALLOWED_IMPORTANCE_NAMES = 12;
    public static final String [] ALLOWED_IMPORTANCE_NAMES = new String [] {
                                                              "g", "G", "green", "Green",
                                                              "y", "Y", "yellow", "Yellow",
                                                               "r", "R", "red", "Red"                                                            
                                                             };
    
    public final String value;
    public final int level;
    
    /**
     * Validates given importance.
     *
     * @throws IllegalValueException if given start importance string is invalid.
     */
    
    public Importance(String importance) throws IllegalValueException {
        assert importance != null;
        importance = importance.trim();
        if (!isValidImportance(importance) && (importance != "")) {
            throw new IllegalValueException(MESSAGE_IMPORTANCE_CONSTRAINTS);
        }
        
        this.value = changeStringIntoProperColorName(importance);
        
        if (value.equals("Green"))
            level = 1;
        else if(value.equals("Yellow"))
            level = 2;
        else if (value.equals("Red"))
            level = 3;
        else
            level = 0;
    }
    
    private String changeStringIntoProperColorName(String importance) {
        if(importance.equals("g") | importance.equals("G") | importance.equals("green"))
            importance = "Green";
        else if (importance.equals("y") | importance.equals("Y") | importance.equals("yellow"))
            importance = "Yellow";
        else if (importance.equals("r") | importance.equals("R") | importance.equals("red"))
            importance = "Red";
        return importance;
    }

    /**
     * Returns true if a given string is a valid task start time.
     */
    public static boolean isValidImportance(String test) {
        
        if(test.matches(IMPORTANCE_VALIDATION_REGEX)) {
            if(Arrays.asList(ALLOWED_IMPORTANCE_NAMES).contains(test)) 
                return true;           
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getLevel() {
        return value;
    }
    
    public int getNumberLevel() {
        return level;
    }
}

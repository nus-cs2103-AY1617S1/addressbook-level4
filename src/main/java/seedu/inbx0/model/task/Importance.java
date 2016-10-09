package seedu.inbx0.model.task;

import seedu.inbx0.commons.exceptions.IllegalValueException;

/**
 * Represents Importance of a Task in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidImportance(String)}
 */

public class Importance {
    
    public static final String MESSAGE_IMPORTANCE_CONSTRAINTS = "Importance has to be alphabetical format";
    public static final String IMPORTANCE_VALIDATION_REGEX = "[\\p{Alpha}]";
    public static final int NUM_OF_STRINGS_IN_ALLOWED_IMPORTANCE_NAMES = 6;
    public static final String [] ALLOWED_IMPORTANCE_NAMES = {"g", "green",
                                                              "y", "yellow",
                                                               "r", "red"                                                            
                                                             };
    
    public final String value;
    
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
        this.value = importance;
    }
    
    /**
     * Returns true if a given string is a valid task start time.
     */
    public static boolean isValidImportance(String test) {
        
        if(test.matches(IMPORTANCE_VALIDATION_REGEX)) {
 //           for(int i = 0; i < NUM_OF_STRINGS_IN_ALLOWED_IMPORTANCE_NAMES; i++) {
  //              if(test == ALLOWED_IMPORTANCE_NAMES[i])
                    return true;
 //           }       
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
}

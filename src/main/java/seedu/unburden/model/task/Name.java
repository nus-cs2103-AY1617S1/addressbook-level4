package seedu.unburden.model.task;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.unburden.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 *  @@author A0143095H
 */


public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Task names should be spaces or alphanumeric characters";
    public static final String NAME_VALIDATION_REGEX = "[A-Za-z0-9 ,.?!\'\"]+";

    private final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
       if (!isValidName(name)) {
    	   System.out.println("error in the constructor");
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = name;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
    	final Pattern pattern = Pattern.compile(NAME_VALIDATION_REGEX);
    	final Matcher matcher = pattern.matcher(test.trim());
        return matcher.matches();
    }
    
    public boolean contains (Set<String> args){
    	for(String name : args){
    		if(fullName.toLowerCase().contains(name.toLowerCase())){
    			return true;
    		}
    	}
    	return false;
    }
    
    public String getFullName() {
    	return this.fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}

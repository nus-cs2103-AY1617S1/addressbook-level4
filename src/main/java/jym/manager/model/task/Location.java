package jym.manager.model.task;


import jym.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Location {
    
    public static final String MESSAGE_LOCATION_CONSTRAINTS = "Locations can be in any format";
    public static final String LOCATION_VALIDATION_REGEX = ".+";

    private final String value;

    
    public Location(){
    	value = "no location";
    }
    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        assert location != null;
//        if (!isValidLocation(location)) {
//            throw new IllegalValueException(MESSAGE_LOCATION_CONSTRAINTS);
//        }
        this.value = location;
    }
    public Location(Location other){
    	this.value = other.toString();
    }
    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
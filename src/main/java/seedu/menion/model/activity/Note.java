//@@author A0139164A
package seedu.menion.model.activity;


import seedu.menion.commons.exceptions.IllegalValueException;

/**
 * Represents an activity's note in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Note {
    
    public String value = "-";

    /**
     * Constructor for a note, takes in new String as note.
     */
    public Note(String note) throws IllegalValueException {
        this.value = note;
    }
    
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && this.value.equals(((Note) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
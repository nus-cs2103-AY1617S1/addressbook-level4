package seedu.cmdo.commons.exceptions;

public class CantDoneBlockedSlotException extends Exception {
    /**
     * @@author A0141128R
     * To not allow users to done a blocked time slot as it is not a task
     */
	
    public CantDoneBlockedSlotException(String message) {
        super(message);
    }
}

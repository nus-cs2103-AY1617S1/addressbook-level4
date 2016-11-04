package seedu.cmdo.commons.exceptions;

//@@author A0141128R
/**
 * To not allow users to done a blocked time slot as it is not a task
 */
public class CantDoneBlockedSlotException extends Exception {

    public CantDoneBlockedSlotException(String message) {
        super(message);
    }
}

package seedu.todo.commons.events.storage;

import seedu.todo.commons.events.BaseEvent;

/**
 * Indicates an exception during a file saving
 */
public class DataSavingExceptionEvent extends BaseEvent {

    public final Exception exception;
    public final String message;

    public DataSavingExceptionEvent(String message, Exception exception) {
        this.message = message;
        this.exception = exception;
    }

    @Override
    public String toString(){
        return message;
    }

}

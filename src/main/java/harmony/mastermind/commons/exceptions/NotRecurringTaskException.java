package harmony.mastermind.commons.exceptions;

//@@author A0124797R
/**
 * Signals that current Task is not recurring Task and not able to perform methods that are
 * for recurring tasks
 */
public class NotRecurringTaskException extends Exception{
    
    public NotRecurringTaskException() {
        super("Task is not a recurring Task");
    }

}

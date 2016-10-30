package harmony.mastermind.commons.exceptions;

//@@author A0138862W
/*
 * This exception is thrown when attempt to execute Unmarkcommand on a task that is not marked at all.
 */
public class TaskAlreadyUnmarkedException extends Exception{

    public TaskAlreadyUnmarkedException(){
        super();
    }
}

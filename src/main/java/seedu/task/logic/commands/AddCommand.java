package seedu.task.logic.commands;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.DateTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends UndoableCommand {

    private static final int MAX_NUMBER_OF_RECURRENCE_WEEK=20;
    
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: NAME s/start-datetime c/closedatetime [t/TAG r/NUMBER_TO_RECUR]...\n"
            + "Example: " + COMMAND_WORD
            + " Finish CS2103";
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_ROLLBACK_SUCCESS = "Added task removed: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";
    public static final String MESSAGE_WRONG_NUMBER_OF_RECURRENCE = "Maximum number of recurrence is 20!";
    public static final String MESSAGE_NEGATIVE_NUMBER_OF_RECURRENCE = "The number recurrence should be positive!";
    private final Task toAdd;
    

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String openTime, String closeTime, Set<String> tags, int recurrence) 
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        this.toAdd = new Task(
                new Name(name),
                new DateTime(openTime),
                new DateTime(closeTime),
                false,
                false,
                new UniqueTagList(tagSet),
                recurrence           
             );
    }

  //@@author A0153467Y
    @Override
    public CommandResult execute() {
        assert model != null;
        try {

            if (toAdd.getRecurrentWeek() > MAX_NUMBER_OF_RECURRENCE_WEEK) {
                return new CommandResult(false, MESSAGE_WRONG_NUMBER_OF_RECURRENCE);
            }
            
            if(toAdd.getRecurrentWeek() < 0) {
                return new CommandResult(false, MESSAGE_NEGATIVE_NUMBER_OF_RECURRENCE);
            }

            if (toAdd.getRecurrentWeek() == 0) {
                model.addTask(toAdd);
                return new CommandResult(true, String.format(MESSAGE_SUCCESS, toAdd));
            } else {
                model.addTask(toAdd);
                createRecurringTask(toAdd.getRecurrentWeek());
            }
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(false, MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(true, String.format(MESSAGE_SUCCESS, toAdd));
    }
    
    //@@author A0153467Y
    private void createRecurringTask(int recurringTime) {
        try{
            for(int i=1;i<=recurringTime;i++) {
            Task recurrTask=new Task(
                toAdd.getName(),
                new DateTime(toAdd.getOpenTime().getDateTimeValue().get().plusSeconds(604800*i).toString()),
                new DateTime(toAdd.getCloseTime().getDateTimeValue().get().plusSeconds(604800*i).toString()),
                false,
                false,
                toAdd.getTags(),
                0
             );
                model.addTask(recurrTask);
            }
        } catch (IllegalValueException e) {
            assert false : "Impossible";
        }
    }
    
    
    
    @Override
    public CommandResult rollback() {
        assert model != null;
        model.rollback();
        
        return new CommandResult(true, String.format(MESSAGE_ROLLBACK_SUCCESS, toAdd));
    }
}

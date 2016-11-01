//@@author A0144939R
package seedu.task.logic.commands;

import java.time.temporal.ChronoUnit;
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
            + "Parameters: NAME starts DATETIME ends DATETIME tag TAG1 tag TAG2 recurs NUMBER_TO_RECUR\n"
            + "Example: " + COMMAND_WORD
            + " Finish CS2103 ends tomorrow starts today tag important tag urgent";
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_ROLLBACK_SUCCESS = "Added task removed: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";
    public static final String MESSAGE_WRONG_NUMBER_OF_RECURRENCE = "Maximum number of recurrence is 20!";
    public static final String MESSAGE_NEGATIVE_NUMBER_OF_RECURRENCE = "The number recurrence should be positive!";
    
    private final Task toAdd;
    private final int amountRecurring;
    

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
        
        this.amountRecurring = recurrence;
        
        this.toAdd = new Task(
                new Name(name),
                DateTime.fromUserInput(openTime),
                DateTime.fromUserInput(closeTime),
                false,
                false,
                new UniqueTagList(tagSet)
                );
    }

    //@@author A0153467Y
    @Override
    public CommandResult execute() {
        assert model != null;
        
        if (this.amountRecurring > MAX_NUMBER_OF_RECURRENCE_WEEK) {
            return new CommandResult(false, MESSAGE_WRONG_NUMBER_OF_RECURRENCE);
        } else if (this.amountRecurring < 0) {
            return new CommandResult(false, MESSAGE_NEGATIVE_NUMBER_OF_RECURRENCE);
        }
        
        try {
            createRecurringTask(this.amountRecurring + 1);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(false, MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException e) {
            assert false : "Not possible!";
        }
        return new CommandResult(true, String.format(MESSAGE_SUCCESS, toAdd));
    }
    
    //@@author A0141052Y
    /**
     * Creates a set number of Tasks, with distance between two adjacent tasks being 1 week, and adds it to the
     * TaskList.
     * @param timesToRecur number of weeks to recur the Task for
     * @throws IllegalValueException if there's a duplicate task or if 
     */
    private void createRecurringTask(int timesToRecur) throws IllegalValueException {
        for (int i = 0; i < timesToRecur; i++) {
            DateTime newOpenTime = DateTime.fromDateTimeOffset(toAdd.getOpenTime(), i * 7, ChronoUnit.DAYS);
            DateTime newCloseTime = DateTime.fromDateTimeOffset(toAdd.getCloseTime(), i * 7, ChronoUnit.DAYS);
            Task newTask = new Task(
                        toAdd.getName(),
                        newOpenTime,
                        newCloseTime,
                        false,
                        false,
                        toAdd.getTags());
            model.addTask(newTask);
        }
    }
    //@author   

    @Override
    public CommandResult rollback() {
        assert model != null;
        model.rollback();
        
        return new CommandResult(true, String.format(MESSAGE_ROLLBACK_SUCCESS, toAdd));
    }
}

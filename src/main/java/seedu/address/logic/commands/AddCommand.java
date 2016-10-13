package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: NAME [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " \"Watch Movie\" t/recreation";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Add Command
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String by, String startTime, String endTime, String recurrence, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        // TODO ensure that we input the correct details!
        //Input validation
        Deadline deadline = new Deadline();
        if(by != null){
            deadline = new Deadline(CommandHelper.convertStringToDate(by));
        }
        Period period = new Period();
        if((startTime != null)&&(endTime != null)){
            List<Date> dates = CommandHelper.convertStringToMultipleDates(startTime + " and " + endTime);
            if(dates.size() < 2){
                throw new IllegalValueException("Invalid Dates");
            }
            period = new Period(dates.get(0), dates.get(1));
        }
        Recurrence deadRecurrence = new Recurrence();
        Recurrence periodRecurrence = new Recurrence();
        if((recurrence != null) && (by != null)){
            deadRecurrence = CommandHelper.getRecurrence(recurrence);
        }
        else if((recurrence != null) && ((startTime != null) && (endTime != null))){
            periodRecurrence = CommandHelper.getRecurrence(recurrence);
        }

        this.toAdd = new Task(
                new Name(name),
                new Complete(false),
                deadline,
                period,
                deadRecurrence,
                periodRecurrence,
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public CommandResult executeUndo() {
        return null;
    }

}

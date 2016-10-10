package seedu.menion.logic.commands;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.activity.*;
import seedu.menion.model.tag.Tag;
import seedu.menion.model.tag.UniqueTagList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an activity to the Menion. "
    		+ "Adding a Floating Task: "+ COMMAND_WORD + "buy lunch n:hawker food\n"
            + "Adding a Task: "+ COMMAND_WORD + "complete cs2103t by: 10-08-2016 1900 n:must complete urgent\n"
    		+ "Adding a Event: "+ COMMAND_WORD + "project meeting from: 10-10-2016 1400 to: 10-10-2016 1800 n:celebrate\n";

    public static final String MESSAGE_SUCCESS = "New activity added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This activity already exists in the Menion";

    private final Activity toAdd = null;
    public final EventStub eventStub;
    public final TaskStub taskStub;
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(ArrayList<String> details) 
            throws IllegalValueException {
    	
    	//this.toAdd = new Activity(details);
        this.eventStub = new EventStub(details);
        this.taskStub = new TaskStub(details);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueActivityList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}

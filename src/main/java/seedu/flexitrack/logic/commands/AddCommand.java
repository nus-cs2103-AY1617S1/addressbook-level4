package seedu.flexitrack.logic.commands;

import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.tag.Tag;
import seedu.flexitrack.model.tag.UniqueTagList;
import seedu.flexitrack.model.task.*;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Adds a task to the FlexiTrack.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_SHORTCUT = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD  + ", Shortcut [" + COMMAND_SHORTCUT + "]" + ": Adds a task to the FlexiTrack.\n"
            + "1. Add Floating Task - Parameters to add an Floating Task: [task title]\n" + "\tExample: " + COMMAND_WORD
            + " Do CS homework\n"
            + "2. Add Event - Parameters to add an event: [task title] from/ [starting time] to/ [ending time]\n"
            + "\tExample: " + COMMAND_WORD + " Summer school from/ 1st June to/ 31st July \n"
            + "3. Add Task - Parameters to add a task: [task title] by/ [due date]\n" + "\tExample: " + COMMAND_WORD
            + " CS tutorial by/ Saturday 10am \n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the FlexiTrack";
    
    static Stack<ReadOnlyTask> storeDataChanged = new Stack<ReadOnlyTask>(); 

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String dueDate, String startTime, String endTime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(new Name(name), new DateTimeInfo(dueDate), new DateTimeInfo(startTime),
                new DateTimeInfo(endTime), new UniqueTagList(tagSet));
    }

    public AddCommand() {
        this.toAdd = null; 
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            
            model.addTask(toAdd);
            storeDataChanged.add(toAdd);
            recordCommand("add"); 
            
            if (toAdd.getIsEvent()) {
                return new CommandResult((String.format(MESSAGE_SUCCESS, toAdd)) + "\n" + DateTimeInfo
                        .durationOfTheEvent(toAdd.getStartTime().toString(), toAdd.getEndTime().toString()));
            } else {
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
            }
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    //TODO: to be implemented 
    public void executeUndo() {
        Task toDelete = new Task (storeDataChanged.peek());

        try {
            model.deleteTask(toDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        
        storeDataChanged.pop();
    }

}

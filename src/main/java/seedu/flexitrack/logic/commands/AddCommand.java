package seedu.flexitrack.logic.commands;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.model.task.Name;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Adds a task to the FlexiTrack.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_SHORTCUT = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ", Shortcut [" + COMMAND_SHORTCUT + "]"
            + ": Adds a task to the FlexiTrack.\n"
            + "1. Add Floating Task - Parameters to add an Floating Task: [task title]\n" + "\tExample: " + COMMAND_WORD
            + " Do CS homework\n"
            + "2. Add Event - Parameters to add an event: [task title] from/ [starting time] to/ [ending time]\n"
            + "\tExample: " + COMMAND_WORD + " Summer school from/ 1st June to/ 31st July \n"
            + "3. Add Task - Parameters to add a task: [task title] by/ [due date]\n" + "\tExample: " + COMMAND_WORD
            + " CS tutorial by/ Saturday 10am \n"
            + "4. Add Recursive Task - Parameters: [task title] fr/[no. of times to recurse] ty/[daily or weekly or monthly] by/[due date]\n"
            + "\tExample: " + COMMAND_WORD + " Complete PC1222 homework fr/5 ty/weekly by/ Friday 2359\n"
            + "5. Add Recursive Event - Parameters: [event title] fr/[no. of times to recurse] ty/[daily or weekly or monthly] from/[start time] to/[ending Time] \n"
            + "\tExample: " + COMMAND_WORD + " Attend PC1222 Lecture fr/10 ty/weekly from/Tue 12pm to/Tue 2pm \n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the FlexiTrack";
    public static final String MESSAGE_OVERLAPPING_EVENT_WARNING = "\nWarning: this event is overlaping a existing event!";
    private static final String MESSAGE_UNDO_SUCCESS = "Undid add: %1$s";

    private Task toAdd;
    private boolean isOverlapping = false;
    int numOfOccurrrence = 0; 
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String dueDate, String startTime, String endTime) throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new DateTimeInfo(dueDate), new DateTimeInfo(startTime),
                new DateTimeInfo(endTime));
    }

    public AddCommand() {
        this.toAdd = null;
    }

    public AddCommand(String name, String dueDate, String startTime, String endTime, int numOfOccurrrence) throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new DateTimeInfo(dueDate), new DateTimeInfo(startTime),
                new DateTimeInfo(endTime));
        this.numOfOccurrrence = numOfOccurrrence;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {

            if (model.checkBlock(toAdd)) {
                return new CommandResult(BlockCommand.MESSAGE_DUPLICATE_TIME);
            }
            this.isOverlapping = model.checkOverlapEvent(toAdd);

            model.addTask(toAdd);
            toAdd = toAdd.copy();
            recordCommand(this);
            
            if (toAdd.getIsEvent()) {
                return new CommandResult((String.format(MESSAGE_SUCCESS, toAdd)) + "\n"
                        + DateTimeInfo.durationOfTheEvent(toAdd.getStartTime().toString(),
                                toAdd.getEndTime().toString())
                        + (isOverlapping ? MESSAGE_OVERLAPPING_EVENT_WARNING : ""));
            } else {
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
            }
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    // @@author A0127686R
    @Override
    public void executeUndo() {
        Task toDelete = toAdd;

        try {
            model.deleteTask(toDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
    }
    
    @Override
    public String getUndoMessage(){
        return String.format(MESSAGE_UNDO_SUCCESS, toAdd);
    }

    @Override
    int getNumOfOccurrrence() {
        return numOfOccurrrence;
    }
    
    @Override
    void setNumOfOccurrrence(int numOfOccurrrence) {
        this.numOfOccurrrence = numOfOccurrrence;
    }
}

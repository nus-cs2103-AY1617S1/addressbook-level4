package seedu.savvytasker.logic.commands;

import seedu.savvytasker.commons.core.EventsCenter;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.commons.events.ui.JumpToListRequestEvent;
import seedu.savvytasker.logic.parser.DateParser.InferredDate;
import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.RecurrenceType;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.task.TaskList.InvalidDateException;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Savvy Tasker. "
            + "Parameters: TASK_NAME [s/START_DATE] [st/START_TIME] [e/END_DATE] [et/END_TIME] [l/LOCATION] [p/PRIORITY_LEVEL]"
            + "[r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD
            + " Project Meeting s/05-10-2016 st/14:00 et/18:00 r/daily n/2 c/CS2103 d/Discuss about roles and milestones";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";
    public static final String MESSAGE_INVALID_START_END = "The end time cannot be earlier than the start time";

    private final String taskName;
    private final InferredDate startDateTime;
    private final InferredDate endDateTime;
    private final String location;
    private final PriorityLevel priority;
    private final RecurrenceType recurringType;
    private final Integer numberOfRecurrence;
    private final String category;
    private final String description;
    
    private Task toAdd;

    //@@author A0139915W
    /**
     * Creates an add command.
     */
    public AddCommand(String taskName, InferredDate startDateTime, InferredDate endDateTime,
            String location, PriorityLevel priority, RecurrenceType recurringType, 
            Integer numberOfRecurrence, String category, String description) {
        this.taskName = taskName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.priority = priority;
        this.recurringType = recurringType;
        this.numberOfRecurrence = numberOfRecurrence;
        this.category = category;
        this.description = description;
    }
    
    private void createTask() {
        final boolean isArchived = false;   // all tasks are first added as active tasks
        final int taskId = 0;               // taskId to be assigned by ModelManager, leave as 0
        
        this.toAdd = new Task(taskId, taskName, startDateTime, endDateTime,
                location, priority, recurringType, numberOfRecurrence,
                category, description, isArchived);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        createTask();

        try {
            Task taskAdded = model.addTask(toAdd);
            int targetIndex = getIndexOfTask(taskAdded);
            if (targetIndex > 0) {
                EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
            } else {
                // GUI should never ever get here
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (InvalidDateException ex) {
            return new CommandResult(MESSAGE_INVALID_START_END);
        }

    }
    
    /**
     * Helper method to retrieve the index of the task in the tasklist that was added.
     * @param task The task to find
     * @return Returns the index of the task in the list, -1 if not found.
     */
    private int getIndexOfTask(Task task) {
        model.updateFilteredListToShowActive(); //because newly added tasks are all active.
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        return lastShownList.indexOf(task);
    }
    //@@author
    
    //@@author A0097627N
    /**
     * Checks if a command can perform undo operations
     * @return true if the command supports undo, false otherwise
     */
    @Override
    public boolean canUndo() {
        return true;
    }

    /**
     * Redo the add command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        execute();
        return false;
    }
    
    /**
     * Undo the add command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        
        UnmodifiableObservableList<Task> lastShownList = model.getFilteredTaskListTask();
        
        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i) == toAdd){
                ReadOnlyTask taskToDelete = lastShownList.get(i);
                try {
                    model.deleteTask(taskToDelete);
                } catch (TaskNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } 
        return false;
    }
    
    /**
     * Check if command is an undo command
     * @return true if the command is an undo operation, false otherwise
     */
    @Override
    public boolean isUndo() {
        return false;
    }
    
    /**
     * Check if command is a redo command
     * @return true if the command is a redo operation, false otherwise
     */
    @Override
    public boolean isRedo(){
        return false;
    }
    //@@author
}

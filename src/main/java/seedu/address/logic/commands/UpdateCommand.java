package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.Detail;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Update a task identified using it's last displayed index from the to do list.
 */
public class UpdateCommand extends Command{

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Update the task identified by the index number used in the last task listing. \n"
            + "Prefix a - sign in front of optional fields you wished to remove.\n"
            + "Parameters: ID [NEW_NAME] [[-]on DATE [tTime]] [[-]by DATE [tTime]] [[-]; a line of new details]\n"
            + "Example: " + COMMAND_WORD + " 2 on 14/10/2017 by 18/10/2017 ";

    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Update Task: %1$s";

    private final int targetIndex;
    
    private final String name;
    private final String detail;
    private final String onDate;
    private final String byDate;
    
    public UpdateCommand(int targetIndex, String name, String onDate, String byDate, String detail) {
        
        this.targetIndex = targetIndex;
        this.name = name;
        this.detail = detail;
        this.onDate = onDate;
        this.byDate = byDate;
    }
    
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getUnmodifiableFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);
        
        try {
            Name newName = this.name == null ? taskToUpdate.getName() : new Name(this.name); 

            Detail newDetail;
            TaskDate newByDate;
            TaskDate newOnDate;
            
            if (detail == null) {
                newDetail = taskToUpdate.getDetail();
            } else {
                newDetail = this.detail.trim().equals("-") ?  new Detail(null) : new Detail(this.detail);
            }
            
            if (byDate == null) {
                newByDate = taskToUpdate.getByDate();
            } else {
                newByDate = this.byDate.trim().equals("-") ?  new TaskDate(null) : new TaskDate(this.byDate);
            }
            
            if (onDate == null) {
                newOnDate = taskToUpdate.getOnDate();
            } else {
                newOnDate = this.onDate.trim().equals("-") ?  new TaskDate(null) : new TaskDate(this.onDate);
            }
            
            Task newTask = new Task(newName, newDetail, taskToUpdate.isDone(), newOnDate, newByDate, taskToUpdate.getTags());
            model.updateTask(taskToUpdate, newTask);
            model.updateFilteredListToShowAll();
            
            return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, newTask));
            
        } catch (TaskNotFoundException pnfe) {
            return new CommandResult(MESSAGE_USAGE);
        
        } catch (IllegalValueException e) {
            return new CommandResult(MESSAGE_USAGE);
        }
        
    }
    
    
    
}

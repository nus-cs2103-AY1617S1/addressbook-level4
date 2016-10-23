package seedu.todo.logic.commands;

import seedu.todo.commons.core.Messages;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.tag.UniqueTagList.DuplicateTagException;
import seedu.todo.model.task.Detail;
import seedu.todo.model.task.Name;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Recurrence;
import seedu.todo.model.task.Recurrence.Frequency;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.TaskDate;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;

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
    private final String onDateTime;
    private final String byDateTime;
    private final String recurrence;
    
    public UpdateCommand(int targetIndex, String name, String onDateTime, 
            String byDateTime, String detail, String recurrence) {
        this.targetIndex = targetIndex;
        this.name = name;
        this.detail = detail;
        this.onDateTime = onDateTime;
        this.byDateTime = byDateTime;
        this.recurrence = recurrence;
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
            Name newName = this.name.equals("") ? taskToUpdate.getName() : new Name(this.name); 

            Detail newDetail;
            TaskDate newByDate;
            TaskDate newOnDate;
            Recurrence newRecurrence;
            
            if (this.detail == null) {
                newDetail = taskToUpdate.getDetail();
            } else {
                newDetail = this.detail.trim().equals("-") ?  new Detail(null) : new Detail(this.detail);
            }
            
            if (this.byDateTime == null) {
                newByDate = taskToUpdate.getByDate();
            } else {
                newByDate = this.byDateTime.trim().equals("-") ?  new TaskDate("", TaskDate.TASK_DATE_BY) : new TaskDate(this.byDateTime, TaskDate.TASK_DATE_BY);
            }
            
            if (this.onDateTime == null) {
                newOnDate = taskToUpdate.getOnDate();
            } else {
                newOnDate = this.onDateTime.trim().equals("-") ?  new TaskDate("", TaskDate.TASK_DATE_ON) : new TaskDate(this.onDateTime, TaskDate.TASK_DATE_ON);
            }
            
            if (this.recurrence == null) {
                newRecurrence = taskToUpdate.getRecurrence();
            } else {
                newRecurrence = this.recurrence.trim().equals("-") ?  new Recurrence(Frequency.NONE) : new Recurrence(Frequency.valueOf(this.recurrence.toUpperCase().trim()));
            }
            
            Task newTask = new Task(newName, newDetail, taskToUpdate.getCompletion(), 
                    newOnDate, newByDate, newRecurrence, taskToUpdate.getTags());
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

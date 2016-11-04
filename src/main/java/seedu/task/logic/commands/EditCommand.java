package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task from the task manager.
 * @@author A0147335E
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
    + ": Edits the task identified by the index number used in the last task listing.\n"
    + "Parameters: INDEX TASKNAME at START_TIME to END_TIME [by DEADLINE] [#TAG...]\n"
    + "Example: " + COMMAND_WORD
    + " 4 tag, school";
    
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edit Task: %1$s";
    
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String EDIT_NAME = "name";
    public static final String EDIT_START_TIME = "start";
    public static final String EDIT_END_TIME = "end";
    public static final String EDIT_DEADLINE = "due";
    public static final String EDIT_TAG = "tag";
    public final int targetIndex;
    //private final Task toEdit;
    private final String toEdit;
    private final String toEditItem;
    private final Set<String> toEditTags;
    
    //    public EditCommand(int targetIndex, String name, String startTime, String endTime, String deadline, Set<String> tags) throws IllegalValueException {
    //        final Set<Tag> tagSet = new HashSet<>();
    //        for (String tagName : tags) {
    //            tagSet.add(new Tag(tagName));
    //        }
    //        this.toEdit = new Task(new Name(name), new StartTime(startTime), new EndTime(endTime), new Deadline(deadline), new UniqueTagList(tagSet), new Status());
    //        this.targetIndex = targetIndex;
    //    }
    
    // @@author A0152958R
    public EditCommand(int targetIndex, String item, String editResult,  Set<String> tags) throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.toEdit = editResult;
        this.toEditItem = item;
        this.toEditTags = tags;
        
    }
    
    
    
    // @@author A0152958R
    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        ReadOnlyTask currentTask = lastShownList.get(targetIndex - 1);
        ReadOnlyTask editedTask = null;
        Task toAdd = null;
        final Set<Tag> tagSet = new HashSet<>();
        switch(this.toEditItem){
            case EDIT_NAME:
                try{
                    toAdd = new Task(new Name(this.toEdit), currentTask.getStartTime(), currentTask.getEndTime(), currentTask.getDeadline(), currentTask.getTags(), currentTask.getStatus(), currentTask.getRecurring());
                }catch(IllegalValueException e){
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                break;
            case EDIT_START_TIME:
                try{
                    toAdd = new Task(currentTask.getName(), new StartTime(this.toEdit), currentTask.getEndTime(), currentTask.getDeadline(), currentTask.getTags(), currentTask.getStatus(), currentTask.getRecurring());
                }catch(IllegalValueException e){
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                break;
            case EDIT_END_TIME:
                try{
                    toAdd = new Task(currentTask.getName(), currentTask.getStartTime(), new EndTime(this.toEdit), currentTask.getDeadline(), currentTask.getTags(), currentTask.getStatus(), currentTask.getRecurring());
                }catch(IllegalValueException e){
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                break;
            case EDIT_DEADLINE:
                try{
                    toAdd = new Task(currentTask.getName(), currentTask.getStartTime(), currentTask.getEndTime(), new Deadline(this.toEdit), currentTask.getTags(), currentTask.getStatus(), currentTask.getRecurring());
                }catch(IllegalValueException e){
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                break;
            case EDIT_TAG:
                try{
                    for (String tagName : this.toEditTags) {
                        tagSet.add(new Tag(tagName));
                    }
                    toAdd = new Task(currentTask.getName(), currentTask.getStartTime(), currentTask.getEndTime(), currentTask.getDeadline(), new UniqueTagList(tagSet), currentTask.getStatus(), currentTask.getRecurring());
                }catch(IllegalValueException e){
                    return new CommandResult("Invalid tag format");
                }
                break;
            default:
                try{
                    for (String tagName : this.toEditTags) {
                        tagSet.add(new Tag(tagName));
                    }
                    toAdd = new Task(currentTask.getName(), currentTask.getStartTime(), currentTask.getEndTime(), currentTask.getDeadline(), new UniqueTagList(tagSet), currentTask.getStatus(), currentTask.getRecurring());
                }catch(IllegalValueException e){
                    return new CommandResult(MESSAGE_DUPLICATE_TASK);
                }
        }
        
        try {
            model.addTask(targetIndex, toAdd);
            editedTask = lastShownList.get(targetIndex);
        }  catch (UniqueTaskList.DuplicateTaskException e) {
            try {
                model.deleteTask(editedTask);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
        
        try {
            model.deleteTask(currentTask);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }
        
        if (isUndo == false) {
            history.getUndoList().add(new RollBackCommand(COMMAND_WORD, toAdd, (Task) currentTask));
        }
        // @author A0147944U-reused
        // Sorts updated list of tasks
        model.autoSortBasedOnCurrentSortPreference();
        // @@author A0152958R
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, toEdit));
    }
    
    
    
    @Override
    public CommandResult execute(int index) {
        return null;
        
    }
    
    
}
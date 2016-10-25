package seedu.whatnow.logic.commands;

import static seedu.whatnow.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.Name;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.TaskDate;
import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Update a task with new description/date/time/tag using it's last displayed index from WhatNow.
 */


public class UpdateCommand extends UndoAndRedo {
    
    public static final String COMMAND_WORD = "update";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Updates the description/date/time/tag of the task identified by the index number used in the last task listing.\n"
            + "Parameters: todo/schedule INDEX (must be a positive integer) description/date/time/tag DESCRIPTION/DATE/TIME/TAG\n"
            + "Example: " + COMMAND_WORD + " todo 1 tag priority low";
    
    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in WhatNow";
    
    private static final Pattern DATE_WITH_SLASH_FORMAT = Pattern.compile("^(([3][0-1])|([1-2][0-9])|([0]??[1-9]))[/](([1][0-2])|([0]??[1-9]))[/]([0-9]{4})$");
    private static final Pattern TIME_FORMAT = Pattern.compile("^(([1][0-2])|([0-9]))((:|\\.)([0-5][0-9]))??((am)|(pm))$");
    private static final Pattern TAG_FORMAT = Pattern.compile("^(t/)");
    private static final Pattern TODAY_OR_TOMORROW = Pattern.compile("^(today|tomorrow)$");
    
    private static final String ARG_TYPE_DESCRIPTION = "description";
    private static final String ARG_TYPE_DATE = "date";
    private static final String ARG_TYPE_TIME = "time";
    private static final String ARG_TYPE_START = "start";
    private static final String ARG_TYPE_END = "end";
    private static final String ARG_TYPE_TAG = "tag";
    private static final String DELIMITER_BLANK_SPACE = " ";
    private static final String TASK_TYPE_TODO = "todo";
    private static final String TASK_TYPE_FLOATING = "floating";
    private static final String TASK_TYPE_NOT_FLOATING = "not_floating";
    private static final String NONE = "none";
    
    public final int targetIndex;
    public final String taskType;
    public final String arg_type;
    public final String arg;
    private Task toUpdate;
    
    public UpdateCommand(String taskType, int targetIndex, String arg_type, String arg) throws IllegalValueException, ParseException {
        this.taskType = taskType;
        this.targetIndex = targetIndex;
        this.arg_type = arg_type;
        this.arg = arg;
        processArg();
    }
    
    public static void printArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(i + ": " + array[i]);
        }
    }
    
    /**
     * Processes the arguments in the update command
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    private void processArg() throws IllegalValueException {
        String newName = "default";
        String date = null;
        String startDate = null;
        String endDate = null;
        String time = null;
        String startTime = null;
        String endTime = null;
        final Set<Tag> tagSet = new HashSet<>();
        
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DESCRIPTION) == 0) {
            newName = arg;
        }
        
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DATE) == 0) {
            if (arg != null) {
                System.out.println("ARG: " + arg);
                String[] argComponents = arg.trim().split(DELIMITER_BLANK_SPACE);

                if (argComponents.length == 1) {
                    date = argComponents[0];
                } else if (argComponents.length == 2) {
                    startDate = argComponents[0];
                    endDate = argComponents[1];
                }
            }
            
            //date = (arg.toUpperCase().compareToIgnoreCase(NONE) == 0) ? null : arg;  
        }
        
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_TIME) == 0) {
            if (arg != null) {
                String[] argComponents = arg.trim().split(DELIMITER_BLANK_SPACE);
                if (argComponents.length == 1) {
                    time = argComponents[0];
                } else if (argComponents.length == 2) {
                    startTime = argComponents[0];
                    endTime = argComponents[1];
                }
            }
            
            //time = (arg.toUpperCase().compareToIgnoreCase(NONE) == 0) ? null : arg;
        }
        
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_TAG) == 0) {
            if (arg != null) {
                Set<String> tags = processTag();
                for (String tagName : tags) {
                    tagSet.add(new Tag(tagName));
                }
            }   
        }

        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DATE) == 0) {
            date = (arg.toUpperCase().compareToIgnoreCase(NONE) == 0) ? null : arg;
        }
        
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DESCRIPTION) == 0) {
            newName = arg;
        }
        
        toUpdate = new Task(new Name(newName), date, null, null, null, null, null, new UniqueTagList(tagSet), null, null);
        
        if (date == null) {
            toUpdate.setTaskType(TASK_TYPE_FLOATING);
        }
        else {
            toUpdate.setTaskType(TASK_TYPE_NOT_FLOATING);
        }
    }
    
    /**
     * Processes the tags in the update command
     */
    private Set<String> processTag() {
        if (arg.isEmpty()) {
            return Collections.emptySet();
        }
        final Collection<String> tagStrings = Arrays.asList(arg.split(DELIMITER_BLANK_SPACE));
        return new HashSet<>(tagStrings);
    }
    
    private void updateTheCorrectField(ReadOnlyTask taskToUpdate) {
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_TAG) == 0) {
            toUpdate.setName(taskToUpdate.getName());
            toUpdate.setTaskDate(taskToUpdate.getTaskDate());
            toUpdate.setStartDate(taskToUpdate.getStartDate());
            toUpdate.setEndDate(taskToUpdate.getEndDate());
            toUpdate.setTaskTime(taskToUpdate.getTaskTime());
            toUpdate.setStartTime(taskToUpdate.getStartTime());
            toUpdate.setEndTime(taskToUpdate.getEndTime());
            toUpdate.setStatus(taskToUpdate.getStatus());
            toUpdate.setTaskType(taskToUpdate.getTaskType());
        }
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DESCRIPTION) == 0) {
            toUpdate.setTags(taskToUpdate.getTags());
            toUpdate.setTaskDate(taskToUpdate.getTaskDate());
            toUpdate.setStartDate(taskToUpdate.getStartDate());
            toUpdate.setEndDate(taskToUpdate.getEndDate());
            toUpdate.setTaskTime(taskToUpdate.getTaskTime());
            toUpdate.setStartTime(taskToUpdate.getStartTime());
            toUpdate.setEndTime(taskToUpdate.getEndTime());
            toUpdate.setStatus(taskToUpdate.getStatus());
            toUpdate.setTaskType(taskToUpdate.getTaskType());
        }
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DATE) == 0) {
            toUpdate.setName(taskToUpdate.getName());
            toUpdate.setTaskTime(taskToUpdate.getTaskTime());
            toUpdate.setStartTime(taskToUpdate.getStartTime());
            toUpdate.setEndTime(taskToUpdate.getEndTime());
            toUpdate.setTags(taskToUpdate.getTags());
            toUpdate.setStatus(taskToUpdate.getStatus());            
        }
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_TIME) == 0) {
            toUpdate.setName(taskToUpdate.getName());
            toUpdate.setTaskDate(taskToUpdate.getTaskDate());
            toUpdate.setStartDate(taskToUpdate.getStartDate());
            toUpdate.setEndDate(taskToUpdate.getEndDate());
            toUpdate.setTags(taskToUpdate.getTags());
            toUpdate.setStatus(taskToUpdate.getStatus());
        }
        
        System.out.println("TO UPDATE: " + toUpdate.toString());
    }
    
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList;
        
        if (taskType.equals(TASK_TYPE_TODO) || taskType.equalsIgnoreCase(TASK_TYPE_FLOATING)) {
            lastShownList = model.getCurrentFilteredTaskList();
        } else {
            lastShownList = model.getCurrentFilteredScheduleList();
        }

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);
        updateTheCorrectField(taskToUpdate);
        
        try {
            model.updateTask(taskToUpdate, toUpdate);
            model.getUndoStack().push(this);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
        model.getUndoStack().push(this);
        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, "\nFrom: " + taskToUpdate + " \nTo: " + toUpdate));
    }

	@Override
	public CommandResult undo() {
		assert model != null;
		model.revertDataUpdate();
		return new CommandResult(UndoCommand.MESSAGE_SUCCESS); 
	}
	@Override
	public CommandResult redo() {
		assert model != null;
		model.revertToPrevDataUpdate();
		return new CommandResult(RedoCommand.MESSAGE_SUCCESS);
	}
}

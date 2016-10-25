package tars.logic.commands;

import tars.commons.core.EventsCenter;
import tars.commons.core.Messages;
import tars.commons.events.ui.TaskAddedEvent;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.util.DateTimeUtil;
import tars.model.task.*;
import tars.model.task.UniqueTaskList.TaskNotFoundException;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to tars.
 * 
 * @@author A0139924W
 * @@author A0140022H
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to tars. "
            + "Parameters: NAME [/dt DATETIME] [/p PRIORITY] [/t TAG] [/r NUM_TIMES FREQUENCY]...\n " 
            + "Example: " + COMMAND_WORD
            + " cs2103 project meeting /dt 05/09/2016 1400 to 06/09/2016 2200 /p h /t project /r 2 every week";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_UNDO = "Removed %1$s";
    public static final String MESSAGE_REDO = "Added %1$s";
    
    private static final int DATETIME_INDEX_OF_ENDDATE = 1;
    private static final int DATETIME_INDEX_OF_STARTDATE = 0;
    private static final int DATETIME_EMPTY_DATE = 0;
    
    private static final int ADDTASK_FIRST_ITERATION = 0;
    private static final int ADDTASK_DEFAULT_NUMTASK = 1;
    private static final String ADDTASK_STRING_EMPTY = "";
    private static final String ADDTASK_STRING_NEWLINE = "\n";

    private static final int RECURRINGSTRING_NOT_EMPTY = 1;
    private static final int RECURRINGSTRING_INDEX_OF_NUMTASK = 0;
    private static final int RECURRINGSTRING_INDEX_OF_FREQUENCY = 2;

    private Task toAdd;
    private ArrayList<Task> toAddArray;
    
    private String conflictingTaskList = ""; 

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws DateTimeException if given dateTime string is invalid.
     */
    public AddCommand(String name, String[] dateTime, String priority, Set<String> tags, String[] recurringString)
            throws IllegalValueException, DateTimeException {

        toAddArray = new ArrayList<Task>();
        
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.toAdd = new Task(
                new Name(name),
                new DateTime(dateTime[0], dateTime[1]),
                new Priority(priority),
                new Status(),
                new UniqueTagList(tagSet)
                );
        
        int numTask = ADDTASK_DEFAULT_NUMTASK;
        if (recurringString != null && recurringString.length > 1) {
            numTask = Integer.parseInt(recurringString[RECURRINGSTRING_INDEX_OF_NUMTASK]);
        }

        for (int i = ADDTASK_FIRST_ITERATION; i < numTask; i++) {
            if (i != ADDTASK_FIRST_ITERATION) {
                if (recurringString != null 
                        && recurringString.length > RECURRINGSTRING_NOT_EMPTY) {
                    if (dateTime[DATETIME_INDEX_OF_STARTDATE] != null 
                            && dateTime[DATETIME_INDEX_OF_STARTDATE].length() > DATETIME_EMPTY_DATE) {
                        dateTime[DATETIME_INDEX_OF_STARTDATE] = 
                                DateTimeUtil.modifyDate(dateTime[DATETIME_INDEX_OF_STARTDATE], 
                                             recurringString[RECURRINGSTRING_INDEX_OF_FREQUENCY]);
                    }
                    if (dateTime[DATETIME_INDEX_OF_ENDDATE] != null 
                            && dateTime[DATETIME_INDEX_OF_ENDDATE].length() > DATETIME_EMPTY_DATE) {
                        dateTime[DATETIME_INDEX_OF_ENDDATE] = 
                                DateTimeUtil.modifyDate(dateTime[DATETIME_INDEX_OF_ENDDATE], 
                                           recurringString[RECURRINGSTRING_INDEX_OF_FREQUENCY]);
                    }
                }
            }
            this.toAdd = new Task(new Name(name), 
                    new DateTime(dateTime[DATETIME_INDEX_OF_STARTDATE], 
                                 dateTime[DATETIME_INDEX_OF_ENDDATE]), 
                    new Priority(priority),
                    new Status(), 
                    new UniqueTagList(tagSet));
            toAddArray.add(toAdd);
        }

    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            for(Task toAdd: toAddArray) {
                conflictingTaskList += model.getTaskConflictingDateTimeWarningMessage(toAdd.getDateTime());
                model.addTask(toAdd);
            }
            model.getUndoableCmdHist().push(this);
            return new CommandResult(messageSummary());
        } catch (DuplicateTaskException e) {
            return new CommandResult(Messages.MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public CommandResult undo() {
        assert model != null;
        try {
            for(Task toAdd: toAddArray) {
                model.deleteTask(toAdd);
            }
            return new CommandResult(String.format(UndoCommand.MESSAGE_SUCCESS, String.format(MESSAGE_UNDO, toAdd)));
        } catch (TaskNotFoundException e) {
            return new CommandResult(
                    String.format(UndoCommand.MESSAGE_UNSUCCESS, Messages.MESSAGE_TASK_CANNOT_BE_FOUND));
        }
    }

    @Override
    public CommandResult redo() {
        assert model != null;
        try {
            for(Task toAdd: toAddArray) {
                model.addTask(toAdd);
            }
            return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS, messageSummary()));
        } catch (DuplicateTaskException e) {
            return new CommandResult(String.format(RedoCommand.MESSAGE_UNSUCCESS, Messages.MESSAGE_DUPLICATE_TASK));
        }
    }
    
    //@@author A0140022H
    private String messageSummary() {
        String summary = ADDTASK_STRING_EMPTY;
        
        for(Task toAdd: toAddArray) {
            summary += String.format(MESSAGE_SUCCESS, toAdd + ADDTASK_STRING_NEWLINE);
        }
               
        if (!conflictingTaskList.isEmpty()) {
            summary +="\n" +   Messages.MESSAGE_CONFLICTING_TASKS_WARNING + conflictingTaskList;
        }
        return summary;
    }

}

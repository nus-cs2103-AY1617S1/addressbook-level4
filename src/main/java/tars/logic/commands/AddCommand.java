package tars.logic.commands;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import tars.commons.core.Messages;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.StringUtil;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;
import tars.model.task.DateTime;
import tars.model.task.DateTime.IllegalDateException;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.model.task.Status;
import tars.model.task.Task;
import tars.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Adds a task to tars.
 * 
 * @@author A0140022H
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a task to tars.\n"
            + "Parameters: <TASK_NAME> [/dt DATETIME] [/p PRIORITY] [/t TAG_NAME ...] [/r NUM_TIMES FREQUENCY]\n "
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
    private ArrayList<Task> taskArray;

    private String conflictingTaskList = "";

    // @@author A0140022H
    /**
     * Convenience constructor using raw values.
     * 
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws DateTimeException if given dateTime string is invalid.
     */
    public AddCommand(String name, String[] dateTime, String priority,
            Set<String> tags, String[] recurringString)
            throws IllegalValueException, DateTimeException {

        taskArray = new ArrayList<Task>();

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        addToTaskArray(name, dateTime, priority, recurringString, tagSet);

    }
    // @@author

    // @@author A0140022H
    private void addToTaskArray(String name, String[] dateTime, String priority,
            String[] recurringString, final Set<Tag> tagSet)
            throws IllegalValueException, IllegalDateException {
        int numTask = ADDTASK_DEFAULT_NUMTASK;
        if (recurringString != null
                && recurringString.length > RECURRINGSTRING_NOT_EMPTY) {
            numTask = Integer.parseInt(
                    recurringString[RECURRINGSTRING_INDEX_OF_NUMTASK]);
        }

        for (int i = ADDTASK_FIRST_ITERATION; i < numTask; i++) {
            if (i != ADDTASK_FIRST_ITERATION) {
                if (recurringString != null
                        && recurringString.length > RECURRINGSTRING_NOT_EMPTY) {
                    modifyDateTime(dateTime, recurringString,
                            DATETIME_INDEX_OF_STARTDATE);
                    modifyDateTime(dateTime, recurringString,
                            DATETIME_INDEX_OF_ENDDATE);
                }
            }
            this.toAdd = new Task(new Name(name),
                    new DateTime(dateTime[DATETIME_INDEX_OF_STARTDATE],
                            dateTime[DATETIME_INDEX_OF_ENDDATE]),
                    new Priority(priority), new Status(),
                    new UniqueTagList(tagSet));
            taskArray.add(toAdd);
        }
    }
    // @@author

    // @@author A0140022H
    private void modifyDateTime(String[] dateTime, String[] recurringString,
            int dateTimeIndex) {
        if (dateTime[dateTimeIndex] != null
                && dateTime[dateTimeIndex].length() > DATETIME_EMPTY_DATE) {
            dateTime[dateTimeIndex] = DateTimeUtil.modifyDate(
                    dateTime[dateTimeIndex],
                    recurringString[RECURRINGSTRING_INDEX_OF_FREQUENCY]);
        }
    }
    // @@author

    // @@author A0140022H
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            addTasks();
            model.getUndoableCmdHist().push(this);
            return new CommandResult(messageSummary());
        } catch (DuplicateTaskException e) {
            return new CommandResult(Messages.MESSAGE_DUPLICATE_TASK);
        }
    }
    // @@author

    // @@author A0140022H
    private void addTasks() throws DuplicateTaskException {
        for (Task toAdd : taskArray) {
            conflictingTaskList +=
                    model.getTaskConflictingDateTimeWarningMessage(
                            toAdd.getDateTime());
            model.addTask(toAdd);

            if (taskArray.size() == ADDTASK_DEFAULT_NUMTASK && ((toAdd
                    .getDateTime().getStartDate() == null
                    && toAdd.getDateTime().getEndDate() != null)
                    || (toAdd.getDateTime().getStartDate() != null
                            && toAdd.getDateTime().getEndDate() != null))) {
                model.updateFilteredTaskListUsingDate(toAdd.getDateTime());
            }
        }
    }

    // @@author A0139924W
    @Override
    public CommandResult undo() {
        assert model != null;
        try {
            for (Task toAdd : taskArray) {
                model.deleteTask(toAdd);
            }
            return new CommandResult(String.format(UndoCommand.MESSAGE_SUCCESS,
                    String.format(MESSAGE_UNDO, toAdd)));
        } catch (TaskNotFoundException e) {
            return new CommandResult(
                    String.format(UndoCommand.MESSAGE_UNSUCCESS,
                            Messages.MESSAGE_TASK_CANNOT_BE_FOUND));
        }
    }

    // @@author A0139924W
    @Override
    public CommandResult redo() {
        assert model != null;
        try {
            for (Task toAdd : taskArray) {
                model.addTask(toAdd);
            }
            return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS,
                    messageSummary()));
        } catch (DuplicateTaskException e) {
            return new CommandResult(
                    String.format(RedoCommand.MESSAGE_UNSUCCESS,
                            Messages.MESSAGE_DUPLICATE_TASK));
        }
    }

    // @@author A0140022H
    private String messageSummary() {
        String summary = ADDTASK_STRING_EMPTY;

        for (Task toAdd : taskArray) {
            summary += String.format(MESSAGE_SUCCESS,
                    toAdd + ADDTASK_STRING_NEWLINE);
        }

        if (!conflictingTaskList.isEmpty()) {
            summary += StringUtil.STRING_NEWLINE
                    + Messages.MESSAGE_CONFLICTING_TASKS_WARNING
                    + conflictingTaskList;
        }
        return summary;
    }
    // @@author

}

package tars.logic.commands;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import tars.commons.core.Messages;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.exceptions.InvalidTaskDisplayedException;
import tars.commons.util.StringUtil;
import tars.model.task.DateTime;
import tars.model.task.Name;
import tars.model.task.rsv.RsvTask;
import tars.model.task.rsv.UniqueRsvTaskList.RsvTaskNotFoundException;
import tars.ui.formatter.Formatter;

/**
 * Adds a reserved task which has a list of reserved date times that can confirmed later on.
 * 
 * @@author A0124333U
 */
public class RsvCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rsv";
    public static final String COMMAND_WORD_DEL = "rsv /del";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reserves one or more timeslot for a task.\n"
            + "Parameters: <TASK_NAME> </dt DATETIME> [/dt DATETIME ...]\n" + "Example: " + COMMAND_WORD
            + " Meet John Doe /dt 26/09/2016 0900 to 1030, 28/09/2016 1000 to 1130";

    public static final String MESSAGE_USAGE_DEL = COMMAND_WORD_DEL
            + ": Deletes a reserved task in the last reserved task listing \n"
            + "Parameters: INDEX (must be a positive integer)\n "
            + "Example: " + COMMAND_WORD_DEL + " 1\n" + "OR "
            + COMMAND_WORD_DEL + " 1..3";

    public static final String MESSAGE_DATETIME_NOT_FOUND =
            "At least one DateTime is required!\n" + MESSAGE_USAGE;

    public static final String MESSAGE_INVALID_RSV_TASK_DISPLAYED_INDEX =
            "The Reserved Task Index is invalid!";

    public static final String MESSAGE_SUCCESS = "New task reserved: %1$s";
    public static final String MESSAGE_SUCCESS_DEL = "Deleted reserved tasks:\n%1$s";
    public static final String MESSAGE_UNDO_DELETE = "Deleted %1$s";
    public static final String MESSAGE_UNDO_ADD = "Added:\n%1$s";
    public static final String MESSAGE_REDO_DELETE = "Deleted:%1$s";
    public static final String MESSAGE_REDO_ADD = "Added %1$s";

    private static final String MESSAGE_CONFLICT_FOR = "\nConflicts for ";

    private RsvTask toReserve = null;
    private String rangeIndexString = "";
    private String conflictingTaskList = "";
    
    private static final int INDEX_OF_ENDDATE = 1;
    private static final int INDEX_OF_STARTDATE = 0;

    private ArrayList<RsvTask> rsvTasksToDelete;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws DateTimeException if given dateTime string is invalid.
     */
    public RsvCommand(String name, Set<String[]> dateTimeStringSet) throws IllegalValueException {

        Set<DateTime> dateTimeSet = new HashSet<>();
        for (String[] dateTimeStringArray : dateTimeStringSet) {
            dateTimeSet.add(new DateTime(dateTimeStringArray[INDEX_OF_STARTDATE], dateTimeStringArray[INDEX_OF_ENDDATE]));
        }

        this.toReserve = new RsvTask(new Name(name), new ArrayList<DateTime>(dateTimeSet));
    }

    public RsvCommand(String rangeIndexString) {
        this.rangeIndexString = rangeIndexString;
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        if (toReserve != null) {
            return addRsvTask();
        } else {
            return delRsvTask();
        }

    }

    private CommandResult addRsvTask() {
        try {
            for (DateTime dt : toReserve.getDateTimeList()) {
                if (!model.getTaskConflictingDateTimeWarningMessage(dt).isEmpty()) {
                    conflictingTaskList += MESSAGE_CONFLICT_FOR + dt.toString() + StringUtil.STRING_COLON;
                    conflictingTaskList += model.getTaskConflictingDateTimeWarningMessage(dt);
                }
            }
            model.addRsvTask(toReserve);
            model.getUndoableCmdHist().push(this);
            return new CommandResult(getSuccessMessageSummary());
        } catch (DuplicateTaskException e) {
            return new CommandResult(Messages.MESSAGE_DUPLICATE_TASK);
        }
    }

    private CommandResult delRsvTask() {
        rsvTasksToDelete = new ArrayList<RsvTask>();

        try {
            rsvTasksToDelete = getRsvTasksFromIndexes(this.rangeIndexString.split(StringUtil.STRING_WHITESPACE));
        } catch (InvalidTaskDisplayedException itde) {
            return new CommandResult(itde.getMessage());
        }
        
        for (RsvTask t : rsvTasksToDelete) {
            try {
                model.deleteRsvTask(t);
            } catch (RsvTaskNotFoundException rtnfe) {
                return new CommandResult(Messages.MESSAGE_RSV_TASK_CANNOT_BE_FOUND);
            }
        }
        
        model.getUndoableCmdHist().push(this);
        String deletedRsvTasksList = new Formatter().formatRsvTaskList(rsvTasksToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS_DEL, deletedRsvTasksList));
    }

    /**
     * Gets Tasks to delete
     * 
     * @param indexes
     * @throws InvalidTaskDisplayedException
     */
    private ArrayList<RsvTask> getRsvTasksFromIndexes(String[] indexes)
            throws InvalidTaskDisplayedException {
        UnmodifiableObservableList<RsvTask> lastShownList = model.getFilteredRsvTaskList();
        ArrayList<RsvTask> rsvTasksList = new ArrayList<RsvTask>();

        for (int i = StringUtil.START_INDEX; i < indexes.length; i++) {
            int targetIndex = Integer.parseInt(indexes[i]);
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                throw new InvalidTaskDisplayedException(
                        Messages.MESSAGE_INVALID_RSV_TASK_DISPLAYED_INDEX);
            }
            RsvTask rsvTask = lastShownList.get(targetIndex - StringUtil.LAST_INDEX);
            rsvTasksList.add(rsvTask);
        }
        return rsvTasksList;
    }

    private String getSuccessMessageSummary() {
        String summary = String.format(MESSAGE_SUCCESS, toReserve.toString());

        if (!conflictingTaskList.isEmpty()) {
            summary += StringUtil.STRING_NEWLINE + Messages.MESSAGE_CONFLICTING_TASKS_WARNING + conflictingTaskList;
        }

        return summary;
    }
    
    //@@author
    
    //@@author A0139924W
    @Override
    public CommandResult undo() {
        if (toReserve != null) {
            try {
                return undoRsvAdd();
            } catch (RsvTaskNotFoundException e) {
                return new CommandResult(String.format(UndoCommand.MESSAGE_UNSUCCESS,
                        Messages.MESSAGE_RSV_TASK_CANNOT_BE_FOUND));
            }
        } else {
            try {
                return undoRsvDelete();
            } catch (DuplicateTaskException e) {
                return new CommandResult(String.format(UndoCommand.MESSAGE_UNSUCCESS,
                        Messages.MESSAGE_DUPLICATE_TASK));
            }
        }
    }

    //@@author A0139924W
    @Override
    public CommandResult redo() {
        if (toReserve != null) {
            try {
                return redoRsvAdd();
            } catch (DuplicateTaskException e) {
                return new CommandResult(String.format(RedoCommand.MESSAGE_UNSUCCESS,
                        Messages.MESSAGE_DUPLICATE_TASK));
            }
        } else {
            try {
                return redoRsvDelete();
            } catch (RsvTaskNotFoundException e) {
                return new CommandResult(String.format(RedoCommand.MESSAGE_UNSUCCESS,
                        Messages.MESSAGE_RSV_TASK_CANNOT_BE_FOUND));
            }
        }
    }

    private CommandResult undoRsvAdd() throws RsvTaskNotFoundException {
        model.deleteRsvTask(toReserve);
        return new CommandResult(String.format(UndoCommand.MESSAGE_SUCCESS,
                String.format(MESSAGE_UNDO_DELETE, toReserve)));
    }
    
    private CommandResult undoRsvDelete() throws DuplicateTaskException {
        for (RsvTask rsvTask : rsvTasksToDelete) {
            model.addRsvTask(rsvTask);
        }

        String addedRsvTasksList = new Formatter().formatRsvTaskList(rsvTasksToDelete);
        return new CommandResult(String.format(UndoCommand.MESSAGE_SUCCESS,
                String.format(MESSAGE_UNDO_ADD, addedRsvTasksList)));
    }
    
    private CommandResult redoRsvAdd() throws DuplicateTaskException {
        model.addRsvTask(toReserve);
        return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS,
                String.format(MESSAGE_REDO_ADD, toReserve)));
    }
    
    private CommandResult redoRsvDelete() throws RsvTaskNotFoundException {
        for (RsvTask rsvTask : rsvTasksToDelete) {
            model.deleteRsvTask(rsvTask);
        }

        String deletedRsvTasksList = new Formatter().formatRsvTaskList(rsvTasksToDelete);
        return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS,
                String.format(MESSAGE_REDO_DELETE, deletedRsvTasksList)));
    }
}
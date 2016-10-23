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
import tars.model.task.DateTime;
import tars.model.task.Name;
import tars.model.task.rsv.RsvTask;
import tars.model.task.rsv.UniqueRsvTaskList.RsvTaskNotFoundException;

/**
 * Adds a reserved task which has a list of reserved datetimes that can
 * confirmed later on.
 * 
 * @@author A0124333U
 */

public class RsvCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rsv";
    public static final String COMMAND_WORD_DEL = "rsv -d";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reserves one or more timeslot for a task.\n"
            + "Parameters: TASK [/dt DATETIME] [ADDITIONAL DATETIME]\n" + "Example: " + COMMAND_WORD
            + " Meet John Doe /dt 26/09/2016 0900 to 1030, 28/09/2016 1000 to 1130";

    public static final String MESSAGE_USAGE_DEL = COMMAND_WORD_DEL
            + ": Deletes a reserved task in the last reserved task listing \n"
            + "Parameters: INDEX (must be a positive integer)\n " + "Example: " + COMMAND_WORD_DEL + " 1\n" + "OR "
            + COMMAND_WORD_DEL + " 1..3";

    public static final String MESSAGE_DATETIME_NOTFOUND = "At least one DateTime is required!\n" + MESSAGE_USAGE;

    public static final String MESSAGE_INVALID_RSV_TASK_DISPLAYED_INDEX = "The Reserved Task Index is invalid!";

    public static final String MESSAGE_SUCCESS = "New task reserved: %1$s";
    public static final String MESSAGE_SUCCESS_DEL = "Deleted Reserved Tasks: %1$s";
    public static final String MESSAGE_UNDO = "Removed %1$s";
    public static final String MESSAGE_REDO = "Reserved %1$s";

    private RsvTask toReserve = null;
    private String rangeIndexString = "";
    private String conflictingTaskList = "";

    private ArrayList<RsvTask> deletedRsvTasks = new ArrayList<RsvTask>();

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     * @throws DateTimeException
     *             if given dateTime string is invalid.
     */

    public RsvCommand(String name, Set<String[]> dateTimeStringSet) throws IllegalValueException {

        Set<DateTime> dateTimeSet = new HashSet<>();
        for (String[] dateTimeStringArray : dateTimeStringSet) {
            dateTimeSet.add(new DateTime(dateTimeStringArray[0], dateTimeStringArray[1]));
        }

        this.toReserve = new RsvTask(new Name(name), new ArrayList<DateTime>(dateTimeSet));

    }

    public RsvCommand(String rangeIndexString) {
        this.rangeIndexString = rangeIndexString;
    }

    @Override
    public CommandResult undo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult redo() {
        // TODO Auto-generated method stub
        return null;
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
                if (model.getTaskConflictingDateTimeWarningMessage(dt) != "") {
                    conflictingTaskList += "\nConflicts for " + dt.toString() + ":";
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
        ArrayList<RsvTask> rsvTasksToDelete = new ArrayList<RsvTask>();

        try {
            rsvTasksToDelete = getRsvTasksFromIndexes(this.rangeIndexString.split(" "));
        } catch (InvalidTaskDisplayedException itde) {
            return new CommandResult(itde.getMessage());
        }
        for (RsvTask t : rsvTasksToDelete) {
            try {
                model.deleteRsvTask(t);
            } catch (RsvTaskNotFoundException rtnfe) {
                return new CommandResult(Messages.MESSAGE_RSV_TASK_CANNOT_BE_FOUND);
            }
            deletedRsvTasks.add(t);
        }
        model.getUndoableCmdHist().push(this);
        String deletedRsvTasksList = CommandResult.formatRsvTasksList(deletedRsvTasks);
        return new CommandResult(String.format(MESSAGE_SUCCESS_DEL, deletedRsvTasksList));
    }

    /**
     * Gets Tasks to delete
     * 
     * @param indexes
     * @return
     * @throws InvalidTaskDisplayedException
     */
    private ArrayList<RsvTask> getRsvTasksFromIndexes(String[] indexes) throws InvalidTaskDisplayedException {
        UnmodifiableObservableList<RsvTask> lastShownList = model.getFilteredRsvTaskList();
        ArrayList<RsvTask> rsvTasksList = new ArrayList<RsvTask>();

        for (int i = 0; i < indexes.length; i++) {
            int targetIndex = Integer.parseInt(indexes[i]);
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                throw new InvalidTaskDisplayedException(Messages.MESSAGE_INVALID_RSV_TASK_DISPLAYED_INDEX);
            }
            RsvTask rsvTask = lastShownList.get(targetIndex - 1);
            rsvTasksList.add(rsvTask);
        }
        return rsvTasksList;
    }

    private String getSuccessMessageSummary() {
        String summary = String.format(MESSAGE_SUCCESS, toReserve.toString());

        if (!conflictingTaskList.isEmpty()) {
            summary += "\n" + Messages.MESSAGE_CONFLICTING_TASKS_WARNING + conflictingTaskList;
        }

        return summary;
    }
}

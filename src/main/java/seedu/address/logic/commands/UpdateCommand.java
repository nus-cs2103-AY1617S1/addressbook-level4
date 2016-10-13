package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.model.task.Complete;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.Period;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Recurrence;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Updates a task identified using it's last displayed index from the task list.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [name NAME] [by DEADLINE] "
            + "[from START_TIME to END_TIME] [repeatdeadline FREQUENCY COUNT] "
            + "[repeattime FREQUENCY COUNT] [tag \"TAG\"...]\n" + "Example: " + COMMAND_WORD
            + " 1 by 15 Sep 3pm";

    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s";
    private static final String MESSAGE_UPDATE_TASK_UNDO_SUCCESS = 
            "Changes to task was reverted. Now: %1$s";
    private static final String MESSAGE_PERIOD_NEED_BOTH_START_AND_END_TIME = 
            "Period needs to have both start and end time.";

    public static final String PREFIX_REMOVE = "remove";

    public static final String KEYWORD_NAME = "name";
    public static final String KEYWORD_DEADLINE = "by";
    public static final String KEYWORD_PERIOD_START_TIME = "from";
    public static final String KEYWORD_PERIOD_END_TIME = "to";
    public static final String KEYWORD_DEADLINE_RECURRENCE = "repeatdeadline";
    public static final String KEYWORD_PERIOD_RECURRENCE = "repeattime";
    public static final String KEYWORD_TAG = "tag";

    public static final String KEYWORD_REMOVE_DEADLINE = PREFIX_REMOVE + KEYWORD_DEADLINE;
    public static final String KEYWORD_REMOVE_START_TIME = PREFIX_REMOVE
            + KEYWORD_PERIOD_START_TIME;
    public static final String KEYWORD_REMOVE_END_TIME = PREFIX_REMOVE + KEYWORD_PERIOD_END_TIME;
    public static final String KEYWORD_REMOVE_DEADLINE_RECURRENCE = PREFIX_REMOVE
            + KEYWORD_DEADLINE_RECURRENCE;
    public static final String KEYWORD_REMOVE_PERIOD_RECURRENCE = PREFIX_REMOVE
            + KEYWORD_PERIOD_RECURRENCE;
    public static final String KEYWORD_REMOVE_TAG = PREFIX_REMOVE + KEYWORD_TAG;

    public static final String[] VALID_KEYWORDS = { COMMAND_WORD, KEYWORD_NAME, KEYWORD_DEADLINE,
            KEYWORD_PERIOD_START_TIME, KEYWORD_PERIOD_END_TIME, KEYWORD_DEADLINE_RECURRENCE,
            KEYWORD_PERIOD_RECURRENCE, KEYWORD_TAG, KEYWORD_REMOVE_DEADLINE,
            KEYWORD_REMOVE_START_TIME, KEYWORD_REMOVE_END_TIME, KEYWORD_REMOVE_DEADLINE_RECURRENCE,
            KEYWORD_REMOVE_PERIOD_RECURRENCE, KEYWORD_REMOVE_TAG };

    public final int targetIndex;

    public final String updatedName;
    public final String updatedBy;
    public final String updatedStartTime;
    public final String updatedEndTime;
    public final String updatedDeadlineRecurrence;
    public final String updatedPeriodRecurrence;
    public final Set<String> tagsToAdd;

    public final boolean removeDeadline;
    public final boolean removePeriod;
    public final boolean removeDeadlineRecurrence;
    public final boolean removePeriodRecurrence;
    public final Set<String> tagsToRemove;

    private ReadOnlyTask oldReadOnlyTask;
    private Task newTask;

    /**
     * Constructor for update command, to update the task details of a task.
     * 
     * Note: Parameters can be null, to indicate that no changes were made to
     * that particular detail.
     * 
     * @param targetIndex of the task to update
     */
    public UpdateCommand(int targetIndex, String newName, String newBy, String newStartTime,
            String newEndTime, String newDeadlineRecurrence, String newPeriodRecurrence,
            Set<String> newTags, boolean removeDeadline, boolean removePeriod,
            boolean removeDeadlineRecurrence, boolean removePeriodRecurrence,
            Set<String> deleteTags) {
        this.targetIndex = targetIndex;

        this.updatedName = newName;
        this.updatedBy = newBy;
        this.updatedStartTime = newStartTime;
        this.updatedEndTime = newEndTime;
        this.updatedDeadlineRecurrence = newDeadlineRecurrence;
        this.updatedPeriodRecurrence = newPeriodRecurrence;
        this.tagsToAdd = newTags;

        this.removeDeadline = removeDeadline;
        this.removePeriod = removePeriod;
        this.removeDeadlineRecurrence = removeDeadlineRecurrence;
        this.removePeriodRecurrence = removePeriodRecurrence;
        this.tagsToRemove = deleteTags;
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        oldReadOnlyTask = lastShownList.get(targetIndex - 1);
        try {
            newTask = updateOldTaskToNewTask(oldReadOnlyTask);
        } catch (IllegalValueException ive) {
            return new CommandResult(ive.getMessage());
        }

        try {
            model.updateTask(oldReadOnlyTask, newTask);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, newTask));
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public CommandResult executeUndo() {
        Task oldTask = new Task(oldReadOnlyTask);

        try {
            model.updateTask(newTask, oldTask);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_UNDO_SUCCESS, oldTask));
    }

    /**
     * Update the task details of the old task by creating a new task with the
     * new details.
     * 
     * @param oldTask to be updated
     * @return the updated task with the new details
     */
    private Task updateOldTaskToNewTask(ReadOnlyTask oldTask) throws IllegalValueException {
        Name newName = oldTask.getName();
        Complete newComplete = oldTask.getComplete();
        Deadline newDeadline = oldTask.getDeadline();
        Period newPeriod = oldTask.getPeriod();
        Recurrence newDeadlineRecurrence = oldTask.getDeadlineRecurrence();
        Recurrence newPeriodRecurrence = oldTask.getPeriodRecurrence();
        UniqueTagList newTags = oldTask.getTags();

        // adding new details
        if (this.updatedName != null) {
            newName = new Name(this.updatedName);
        }
        if (this.updatedBy != null) {
            newDeadline = new Deadline(CommandHelper.convertStringToDate(this.updatedBy));
        }
        if (this.updatedStartTime != null) {
            if (newPeriod.getEndTime() == null && this.updatedEndTime == null) {
                throw new IllegalValueException(MESSAGE_PERIOD_NEED_BOTH_START_AND_END_TIME);
            } else if (newPeriod.getEndTime() == null) {
                newPeriod = new Period(CommandHelper.convertStringToDate(this.updatedStartTime),
                        CommandHelper.convertStringToDate(this.updatedEndTime));
            } else {
                newPeriod = new Period(CommandHelper.convertStringToDate(this.updatedStartTime),
                        newPeriod.getEndTime());
            }
        }
        if (this.updatedEndTime != null) {
            if (newPeriod.getStartTime() == null) {
                throw new IllegalValueException(MESSAGE_PERIOD_NEED_BOTH_START_AND_END_TIME);
            }

            newPeriod = new Period(newPeriod.getStartTime(),
                    CommandHelper.convertStringToDate(this.updatedEndTime));
        }
        if (this.updatedDeadlineRecurrence != null) {
            newDeadlineRecurrence = CommandHelper.getRecurrence(this.updatedDeadlineRecurrence);
        }
        if (this.updatedPeriodRecurrence != null) {
            newPeriodRecurrence = CommandHelper.getRecurrence(this.updatedPeriodRecurrence);
        }
        if (this.tagsToAdd != null) {
            for (String updatedTag : this.tagsToAdd) {
                try {
                    newTags.add(new Tag(updatedTag));
                } catch (DuplicateTagException dte) {
                    // do nothing (it is fine for the user to accidentally add
                    // the same tag)
                }
            }
        }

        // removing new details
        if (this.removeDeadline) {
            newDeadline = new Deadline();
        }
        if (this.removePeriod) {
            newPeriod = new Period();
        }
        if (this.removeDeadlineRecurrence) {
            newDeadlineRecurrence = new Recurrence();
        }
        if (this.removePeriodRecurrence) {
            newPeriodRecurrence = new Recurrence();
        }
        if (this.tagsToRemove != null) {
            for (String tagToRemove : this.tagsToRemove) {
                try {
                    newTags.remove(new Tag(tagToRemove));
                } catch (TagNotFoundException tnfe) {
                    // do nothing
                    // TODO really do nothing?
                }
            }
        }

        return new Task(newName, newComplete, newDeadline, newPeriod, newDeadlineRecurrence,
                newPeriodRecurrence, newTags);
    }
}

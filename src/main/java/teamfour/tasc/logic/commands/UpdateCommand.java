package teamfour.tasc.logic.commands;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.core.Messages;
import teamfour.tasc.commons.core.UnmodifiableObservableList;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.commons.util.CollectionUtil;
import teamfour.tasc.logic.LogicManager;
import teamfour.tasc.model.keyword.UpdateCommandKeyword;
import teamfour.tasc.model.tag.Tag;
import teamfour.tasc.model.tag.UniqueTagList;
import teamfour.tasc.model.tag.UniqueTagList.DuplicateTagException;
import teamfour.tasc.model.tag.exceptions.TagNotFoundException;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Name;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Recurrence;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Updates a task identified using it's last displayed index from the task list.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = UpdateCommandKeyword.keyword;

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
    public static final String KEYWORD_RECURRENCE = "repeat";
    public static final String KEYWORD_TAG = "tag";

    public static final String KEYWORD_REMOVE_DEADLINE = PREFIX_REMOVE + KEYWORD_DEADLINE;
    public static final String KEYWORD_REMOVE_START_TIME = PREFIX_REMOVE
            + KEYWORD_PERIOD_START_TIME;
    public static final String KEYWORD_REMOVE_END_TIME = PREFIX_REMOVE + KEYWORD_PERIOD_END_TIME;
    public static final String KEYWORD_REMOVE_RECURRENCE = PREFIX_REMOVE
            + KEYWORD_RECURRENCE;
    public static final String KEYWORD_REMOVE_TAG = PREFIX_REMOVE + KEYWORD_TAG;

    public static final String[] VALID_KEYWORDS = { COMMAND_WORD, KEYWORD_NAME, KEYWORD_DEADLINE,
            KEYWORD_PERIOD_START_TIME, KEYWORD_PERIOD_END_TIME, KEYWORD_RECURRENCE, KEYWORD_TAG,
            KEYWORD_REMOVE_DEADLINE, KEYWORD_REMOVE_START_TIME, KEYWORD_REMOVE_END_TIME,
            KEYWORD_REMOVE_RECURRENCE, KEYWORD_REMOVE_TAG };

    public final int targetIndex;

    public final String updatedName;
    public final String updatedBy;
    public final String updatedStartTime;
    public final String updatedEndTime;
    public final String updatedRecurrence;
    public final Set<String> tagsToAdd;

    public final boolean removeDeadline;
    public final boolean removePeriod;
    public final boolean removeRecurrence;
    public final Set<String> tagsToRemove;
    
    private final Logger logger = LogsCenter.getLogger(UpdateCommand.class);

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
            String newEndTime, String newRecurrence, Set<String> newTags, 
            boolean removeDeadline, boolean removePeriod, boolean removeRecurrence,
            Set<String> deleteTags) {
        this.targetIndex = targetIndex;

        this.updatedName = newName;
        this.updatedBy = newBy;
        this.updatedStartTime = newStartTime;
        this.updatedEndTime = newEndTime;
        this.updatedRecurrence = newRecurrence;
        this.tagsToAdd = newTags;

        this.removeDeadline = removeDeadline;
        this.removePeriod = removePeriod;
        this.removeRecurrence = removeRecurrence;
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
        Recurrence newRecurrence = oldTask.getRecurrence();
        UniqueTagList newTags = oldTask.getTags();

        // adding new details
        if (this.updatedName != null) {
            newName = new Name(this.updatedName);
        }
        if (this.updatedBy != null) {
            newDeadline = new Deadline(CommandHelper.convertStringToDate(this.updatedBy));
        }
        
        if (this.updatedStartTime != null || this.updatedEndTime != null) {
            if (this.updatedStartTime == null && newPeriod.getStartTime() == null) {
                throw new IllegalValueException(MESSAGE_PERIOD_NEED_BOTH_START_AND_END_TIME);
            }
            if (this.updatedEndTime == null && newPeriod.getEndTime() == null) {
                throw new IllegalValueException(MESSAGE_PERIOD_NEED_BOTH_START_AND_END_TIME);
            }
            
            // TODO magic string
            // TODO SLAP
            // TODO better test this!
            String finalDateString = "";
            int startDateIndex = 0;
            int endDateIndex = 1;
            
            if (this.updatedStartTime != null && this.updatedEndTime != null) {
                finalDateString = this.updatedStartTime + " and " + this.updatedEndTime;
            }
            else if (this.updatedStartTime != null) {
                // must do it the other way round, otherwise Pretty Time will get confused
                finalDateString = CommandHelper.convertDateToPrettyTimeParserFriendlyString(newPeriod.getEndTime()) +
                        " and " + this.updatedStartTime;
                startDateIndex = 1;
                endDateIndex = 0;
            }
            else if (this.updatedEndTime != null) {
                finalDateString = CommandHelper.convertDateToPrettyTimeParserFriendlyString(newPeriod.getStartTime()) +
                        " and " + this.updatedEndTime;
            }
            
            List<Date> finalOutput = CommandHelper.convertStringToMultipleDates(finalDateString);
            
            if (finalOutput.size() == 2) {                    
                newPeriod = new Period(finalOutput.get(startDateIndex), finalOutput.get(endDateIndex));
            } else {
                throw new IllegalValueException(MESSAGE_PERIOD_NEED_BOTH_START_AND_END_TIME);
            }
        }
        
        if (this.updatedRecurrence != null) {
            newRecurrence = CommandHelper.getRecurrence(this.updatedRecurrence);
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
        if (this.removeRecurrence) {
            newRecurrence = new Recurrence();
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

        return new Task(newName, newComplete, newDeadline, newPeriod, newRecurrence, newTags);
    }
}

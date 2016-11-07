package seedu.taskmaster.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.taskmaster.commons.core.EventsCenter;
import seedu.taskmaster.commons.events.ui.JumpToListRequestEvent;
import seedu.taskmaster.commons.exceptions.IllegalValueException;
import seedu.taskmaster.commons.util.CollectionUtil;
import seedu.taskmaster.model.tag.Tag;
import seedu.taskmaster.model.tag.UniqueTagList;
import seedu.taskmaster.model.task.Name;
import seedu.taskmaster.model.task.RecurringType;
import seedu.taskmaster.model.task.Task;
import seedu.taskmaster.model.task.TaskDate;
import seedu.taskmaster.model.task.UniqueTaskList;
import seedu.taskmaster.model.task.UniqueTaskList.TimeslotOverlapException;

//@@author A0135782Y
/**
 * Adds a non floating task to the task list
 */
public class AddNonFloatingCommand extends AddCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a non floating task to the task list. "
            + "Parameters: TASK_NAME from START_DATE to END_DATE [RECURRING_TYPE] [repeat RECURRING_PERIOD] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " do homework from 2 oct 2am to 3 oct 3am daily t/highPriority";

    public static final String MESSAGE_SUCCESS = "New non-floating task added: %1$s";
    public static final String MESSAGE_TIMESLOT_OCCUPIED = "This timeslot is already blocked or overlapped with existing tasks.";
    public static final String MESSAGE_ILLEGAL_TIME_SLOT = "End time must be later than Start time.";
    
    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     * @param recurringType 
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddNonFloatingCommand(String name, Set<String> tags, TaskDate startDate, TaskDate endDate,
           RecurringType recurringType, int recurringPeriod) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, tags, startDate, endDate, recurringType);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(new Name(name), new UniqueTagList(tagSet),
                              new TaskDate(startDate), new TaskDate(endDate),
                              recurringType, recurringPeriod);
        if (!this.toAdd.getLastAppendedComponent().isValidNonFloatingTime()) {
        	indicateAttemptToExecuteIncorrectCommand();
        	throw new IllegalValueException(MESSAGE_ILLEGAL_TIME_SLOT);
        }
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            CommandResult result = new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
            int targetIndex = model.getFilteredTaskComponentList().size();
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - INDEX_OFFSET));
            return result;
        } catch (UniqueTaskList.DuplicateTaskException e) {
        	indicateAttemptToExecuteFailedCommand();
        	urManager.popFromUndoQueue();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (TimeslotOverlapException e) {
        	indicateAttemptToExecuteFailedCommand();
        	urManager.popFromUndoQueue();
        	return new CommandResult(MESSAGE_TIMESLOT_OCCUPIED);
		}
    }
}

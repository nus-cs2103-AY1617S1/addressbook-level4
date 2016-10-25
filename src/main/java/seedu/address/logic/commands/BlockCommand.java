package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;

//@@author A0147967J
/**
 * Blocks a certain time slot in the task list. 
 */
public class BlockCommand extends Command {
	
	public static final String COMMAND_WORD = "block";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Blocks a certain time slot in the schedule. "
            + "Parameters: TASK_NAME [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " from 2 oct 2am to 2 oct 3am t/highPriority";

    public static final String MESSAGE_SUCCESS = "Timeslot blocked: %1$s";
    public static final String MESSAGE_TIMESLOT_OCCUPIED = "This timeslot is already blocked or overlapped with existing tasks.";
    public static final String MESSAGE_ILLEGAL_TIME_SLOT = "End time must be later than Start time.";
    public static final String DUMMY_NAME = "BLOCKED SLOT";



    private final Task toBlock;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public BlockCommand(Set<String> tags, TaskDate startDate, TaskDate endDate)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toBlock = new Task(
                new Name(DUMMY_NAME),
                new UniqueTagList(tagSet),
                new TaskDate(startDate),
                new TaskDate(endDate),
                RecurringType.NONE
        );
        if(!this.toBlock.getComponentForNonRecurringType().isValidTimeSlot()){
        	indicateAttemptToExecuteIncorrectCommand();
        	throw new IllegalValueException(MESSAGE_ILLEGAL_TIME_SLOT);
        }
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toBlock);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toBlock));
        } catch (TimeslotOverlapException e) {
        	indicateAttemptToExecuteFailedCommand();
        	urManager.popFromUndoQueue();
        	return new CommandResult(MESSAGE_TIMESLOT_OCCUPIED);
		} catch (DuplicateTaskException e) {
			assert false: "not applicable for block command";
			return new CommandResult(MESSAGE_TIMESLOT_OCCUPIED);
		}

    }

}

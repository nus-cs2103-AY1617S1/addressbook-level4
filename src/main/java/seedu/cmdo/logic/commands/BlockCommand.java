package seedu.cmdo.logic.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import seedu.cmdo.commons.exceptions.IllegalValueException;
import seedu.cmdo.commons.exceptions.TaskBlockedException;
import seedu.cmdo.logic.parser.Blocker;
import seedu.cmdo.model.tag.Tag;
import seedu.cmdo.model.tag.UniqueTagList;
import seedu.cmdo.model.task.Detail;
import seedu.cmdo.model.task.DueByDate;
import seedu.cmdo.model.task.DueByTime;
import seedu.cmdo.model.task.Priority;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;
import seedu.cmdo.model.task.UniqueTaskList;
/**
 * Created an Block command
 *
 * @throws IllegalValueException if any of the raw values are invalid
 * 
 * @@author A0141128R
 */

public class BlockCommand extends Command {

    public static final String COMMAND_WORD = "block";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Blocks a timeslot in the address book. "
            + "Parameters: <details> by/on <date> at <time> /<priority> /<TAG...>\n"
            + "Example: " + COMMAND_WORD
            + " unconfirmed business meeting on Thursday at noon to 1300 /high -$$$";

    public static final String MESSAGE_SUCCESS = "Time slot blocked: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This time slot if already booked";

    private final Task toBlock;
    
    /**
     * Created an Block command
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public BlockCommand(String details,
                      LocalDate dueByDateStart,
                      LocalTime dueByTimeStart,
                      LocalDate dueByDateEnd,
                      LocalTime dueByTimeEnd,
                      String priority,
                      Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toBlock = new Task(
                new Detail(details),
                new DueByDate (dueByDateStart, dueByDateEnd),
                new DueByTime(dueByTimeStart, dueByTimeEnd),
                new Priority(priority),
                new UniqueTagList(tagSet)
        );
        //makes the task a block time slot
        toBlock.setBlock();
    }


    public BlockCommand(Task toBlock) {
        this.toBlock = toBlock;
    }

    public ReadOnlyTask getBlock() {
        return toBlock;
    }

    @Override
    public CommandResult execute() {
        Blocker blocker = new Blocker();
        try {
    		blocker.checkBlocked(toBlock, model.getBlockedList());
            model.addTask(toBlock);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toBlock));
        } catch (TaskBlockedException tbe) {
        	return new CommandResult (tbe.getMessage());
        }
    }

}
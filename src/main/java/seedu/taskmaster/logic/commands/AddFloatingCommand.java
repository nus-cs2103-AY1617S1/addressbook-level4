package seedu.taskmaster.logic.commands;

import seedu.taskmaster.commons.core.EventsCenter;
import seedu.taskmaster.commons.events.ui.JumpToListRequestEvent;
import seedu.taskmaster.commons.exceptions.IllegalValueException;
import seedu.taskmaster.model.tag.Tag;
import seedu.taskmaster.model.tag.UniqueTagList;
import seedu.taskmaster.model.task.*;
import seedu.taskmaster.model.task.UniqueTaskList.TimeslotOverlapException;

import java.util.HashSet;
import java.util.Set;

//@@author A0135782Y
/**
 * Adds a floating task to the task list.
 */
public class AddFloatingCommand extends AddCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a floating task to the task list. "
            + "Parameters: TASK_NAME [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " take trash t/highPriority";

    public static final String MESSAGE_SUCCESS = "New floating task added: %1$s";
    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddFloatingCommand(String name, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(new Name(name), new UniqueTagList(tagSet));
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
			assert false: "not possible";
        	return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
		}
    }
}

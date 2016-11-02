package seedu.emeraldo.logic.commands;

import seedu.emeraldo.commons.core.EventsCenter;
import seedu.emeraldo.commons.core.UnmodifiableObservableList;
import seedu.emeraldo.commons.events.ui.JumpToListRequestEvent;
import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.tag.Tag;
import seedu.emeraldo.model.tag.UniqueTagList;
import seedu.emeraldo.model.task.*;

import java.time.DateTimeException;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public int targetIndex;
    
    public static final String COMMAND_WORD = "add";
        
    //@@author A0139749L
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: \"TASK_DESCRIPTION\" [on DATE] [by DATE_TIME] [from START_DATE_TIME]"
            + "[to END_DATE_TIME] [#TAGS]...\n"
            + "Example: " + COMMAND_WORD
            + " \"CS2103T Lecture\" on 7 Oct 2016 #Important\n"
            + "                add \"Sports Camp\" from 12 Dec, 10am to 14 Dec, 8pm";
    //@@author
    
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws DateTimeException if any of the day does not match the month and year
     */
    public AddCommand(String description, String dateTime, Set<String> tags)
            throws IllegalValueException, DateTimeException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Description(description),
                new DateTime(dateTime.trim()),
                new UniqueTagList(tagSet)
        );
    }

    //@@author A0139196U
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            
            // Emeraldo will select the newly added task and jump to it to show and confirm with the user that it is added
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            targetIndex = lastShownList.size();
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}

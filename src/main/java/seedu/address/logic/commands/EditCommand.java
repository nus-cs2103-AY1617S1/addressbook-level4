package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Entry;
import seedu.address.model.person.FloatingTask;
import seedu.address.model.person.Title;
import seedu.address.model.person.UniquePersonList.DuplicateTaskException;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;

/*
 * Edit a task's content.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits tasks details. " + "Parameters: TASK_ID"
            + "[TITLE]" + "Example: " + COMMAND_WORD + " 2 Buy bread";

    public static final String MESSAGE_SUCCESS = "Edited entry: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This entry already exists in the todo list";

    public final int targetIndex;

    public final Title newTitle;

    public EditCommand(int targetIndex, String title) throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.newTitle = new Title(title);
    }

    @Override
    public CommandResult execute() {   
        UnmodifiableObservableList<Entry> lastShownList = model.getFilteredPersonList();
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }
        
        Entry taskToEdit = lastShownList.get(targetIndex - 1);
        assert model != null;
        try {
            model.editTask(taskToEdit, newTitle);
        } catch (PersonNotFoundException e) {
            assert false : "The target entry cannot be missing";
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToEdit));
    }

}

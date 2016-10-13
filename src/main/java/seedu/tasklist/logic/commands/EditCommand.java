package seedu.tasklist.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Description;
import seedu.tasklist.model.task.DueDate;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.StartDate;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.Title;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the task list.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX a[TITLE] [d/DESCRIPTION] [s/START DATE] [e/DUE DATE] [t/TAG]\n" + "Example: "
            + COMMAND_WORD + " 1 d/new description";

    private static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";

    public final int targetIndex;
    private final Task toEdit;

    public EditCommand(int targetIndex, String title, String startDate, String description, String dueDate,
            Set<String> tags) throws IllegalValueException {
        this.targetIndex = targetIndex;

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toEdit = new Task(new Title(title), new StartDate(startDate), new Description(description),
                new DueDate(dueDate), new UniqueTagList(tagSet));
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);

        Task editedTask = editTask(taskToEdit);

        try {
            model.editTask(editedTask, taskToEdit);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Combine the new editions with its original task
     * @param taskToEdit containing the parameters to change
     * @return new edited task
     */
    private Task editTask(ReadOnlyTask taskToEdit) {
        return new Task(
                this.toEdit.getTitle().toString().equals("") ? taskToEdit.getTitle() : this.toEdit.getTitle(),
                this.toEdit.getStartDate().toString().equals("") ? taskToEdit.getStartDate() : this.toEdit.getStartDate(),
                this.toEdit.getDescription().toString().equals("") ? taskToEdit.getDescription() : this.toEdit.getDescription(),
                this.toEdit.getDueDate().toString().equals("") ? taskToEdit.getDueDate() : this.toEdit.getDueDate(),
                this.toEdit.getTags().getInternalList().isEmpty() ? new UniqueTagList(taskToEdit.getTags()) : new UniqueTagList(this.toEdit.getTags())
                        );
    }

}

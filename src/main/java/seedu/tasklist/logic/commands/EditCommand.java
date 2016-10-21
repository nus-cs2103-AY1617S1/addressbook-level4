package seedu.tasklist.logic.commands;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.DateTime;
import seedu.tasklist.model.task.Description;
import seedu.tasklist.model.task.ReadOnlyTask;
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

    private int targetIndex;
    private Task toEdit;

    public EditCommand() {};
    
    public EditCommand(int targetIndex, String title, String startDate, String description, String dueDate,
            Set<String> tags) throws IllegalValueException {
        this.targetIndex = targetIndex;

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        this.toEdit = new Task(new Title(title), new DateTime(startDate), new Description(description),
                new DateTime(dueDate), new UniqueTagList(tagSet));
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);

        try {
            Task editedTask = editTask(taskToEdit);
            model.editTask(editedTask, taskToEdit);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (IllegalValueException e) {
            assert false : "The target task cannot be illegal";
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Combine the new editions with its original task
     * @param taskToEdit containing the parameters to change
     * @return new edited task
     * @throws IllegalValueException 
     */
    private Task editTask(ReadOnlyTask taskToEdit) throws IllegalValueException {
        DateTime startDateTime = new DateTime();
        startDateTime.setDate(this.toEdit.getStartDateTime().getDate().toString().equals("") ? taskToEdit.getStartDateTime().getDate() : this.toEdit.getStartDateTime().getDate());
        startDateTime.setTime(this.toEdit.getStartDateTime().getTime().toString().equals("") ? taskToEdit.getStartDateTime().getTime() : this.toEdit.getStartDateTime().getTime());
        
        DateTime endDateTime = new DateTime();
        endDateTime.setDate(this.toEdit.getEndDateTime().getDate().toString().equals("") ? taskToEdit.getEndDateTime().getDate() : this.toEdit.getEndDateTime().getDate());
        endDateTime.setTime(this.toEdit.getEndDateTime().getTime().toString().equals("") ? taskToEdit.getEndDateTime().getTime() : this.toEdit.getEndDateTime().getTime());
        
        return new Task(
                this.toEdit.getTitle().toString().equals("") ? taskToEdit.getTitle() : this.toEdit.getTitle(),
                startDateTime,
                this.toEdit.getDescription().toString().equals("") ? taskToEdit.getDescription() : this.toEdit.getDescription(),
                endDateTime,
                this.toEdit.getTags().getInternalList().isEmpty() ? new UniqueTagList(taskToEdit.getTags()) : new UniqueTagList(this.toEdit.getTags())
                        );
    }

    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepare(String args) {
        final Matcher matcher = EDIT_TASK_DATA_ARGS_FORMAT.matcher(args.trim());

        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        
        // Validate arg index
        Optional<Integer> index = parseIndex(matcher.group("targetIndex"));
        
        if(!index.isPresent()){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        
        try {            
            return new EditCommand(
                    index.get(),
                    matcher.group("title"),
                    getDetailsFromArgs(matcher.group("startDate")),
                    getDetailsFromArgs(matcher.group("description")),
                    getDetailsFromArgs(matcher.group("dueDate")),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}

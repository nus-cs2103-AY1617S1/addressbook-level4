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
            + "Parameters: INDEX [TITLE] [d/DESCRIPTION] [s/START DATE] [e/DUE DATE] [t/TAG]\n" + "Example: "
            + COMMAND_WORD + " 1 NEW_TITLE d/NEW_DESCRIPTION\n"
            + COMMAND_WORD + " 2 e/12122012 2359"
            + COMMAND_WORD + " 3 t/TAG1 t/TAG2";

    private static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";

    private int targetIndex;
    private Task toEdit;

    public EditCommand() {};
    
    public EditCommand(int targetIndex, String title, String startDateTime, String description, String endDateTime,
            Set<String> tags) throws IllegalValueException {
        this.targetIndex = targetIndex;

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        this.toEdit = new Task(new Title(title), new DateTime(startDateTime), new Description(description),
                new DateTime(endDateTime), new UniqueTagList(tagSet));
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
     * @throws IllegalValueException if input contains invalid format
     */
    private Task editTask(ReadOnlyTask taskToEdit) throws IllegalValueException {
        return new Task(editTitle(taskToEdit), editStartDateTime(taskToEdit), editDescription(taskToEdit),
                editEndDateTime(taskToEdit), editTags(taskToEdit));
    }
    
    /**
     * Retrieve a Title by replacing the original Title with the new Title, if any.
     * @param taskToEdit containing the parameters to change
     * @return Title with the edited Title
     */
    private Title editTitle(ReadOnlyTask taskToEdit) {
        return this.toEdit.getTitle().toString().equals("") ? taskToEdit.getTitle() : this.toEdit.getTitle();
    }
    
    /**
     * Retrieve a Description by replacing the original Description with the new Description, if any.
     * @param taskToEdit containing the parameters to change
     * @return Description with the edited Description
     */
    private Description editDescription(ReadOnlyTask taskToEdit) {
        return this.toEdit.getDescription().toString().equals("") ? taskToEdit.getDescription() : this.toEdit.getDescription();
    }
    
    /**
     * Retrieve a DateTime by replacing the original DateTime with the new DateTime, if any.
     * @param taskToEdit containing the parameters to change
     * @return DateTime with the edited DateTime 
     * @throws IllegalValueException if invalid DateTime format
     */
    private DateTime editStartDateTime(ReadOnlyTask taskToEdit) throws IllegalValueException {
        DateTime startDateTime = new DateTime();
        startDateTime.setDate(this.toEdit.getStartDateTime().getDate().toString().equals("") ? taskToEdit.getStartDateTime().getDate() : this.toEdit.getStartDateTime().getDate());
        startDateTime.setTime(this.toEdit.getStartDateTime().getTime().toString().equals("") ? taskToEdit.getStartDateTime().getTime() : this.toEdit.getStartDateTime().getTime());
        return startDateTime;
    }
    
    /**
     * Retrieve a DateTime by replacing the original DateTime with the new DateTime, if any.
     * @param taskToEdit containing the parameters to change
     * @return DateTime with the edited DateTime 
     * @throws IllegalValueException if invalid DateTime format
     */
    private DateTime editEndDateTime(ReadOnlyTask taskToEdit) throws IllegalValueException {
        DateTime endDateTime = new DateTime();
        endDateTime.setDate(this.toEdit.getEndDateTime().getDate().toString().equals("") ? taskToEdit.getEndDateTime().getDate() : this.toEdit.getEndDateTime().getDate());
        endDateTime.setTime(this.toEdit.getEndDateTime().getTime().toString().equals("") ? taskToEdit.getEndDateTime().getTime() : this.toEdit.getEndDateTime().getTime());
        return endDateTime;
    }
    
    /**
     * Retrieve a UniqueTagList by replacing the original Tags with the new Tags, if any.
     * @param taskToEdit containing the parameters to change
     * @return UniqueTagList containing the edited Tags
     */
    private UniqueTagList editTags(ReadOnlyTask taskToEdit) {
        return this.toEdit.getTags().getInternalList().isEmpty() ? new UniqueTagList(taskToEdit.getTags()) : new UniqueTagList(this.toEdit.getTags());
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

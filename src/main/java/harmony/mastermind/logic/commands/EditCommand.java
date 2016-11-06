package harmony.mastermind.logic.commands;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.commons.exceptions.InvalidEventDateException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.TaskBuilder;
import harmony.mastermind.model.task.UniqueTaskList;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task in task manager
 *
 */
public class EditCommand extends Command implements Undoable, Redoable {

    public static final String COMMAND_KEYWORD_EDIT = "edit";
    public static final String COMMAND_KEYWORD_UPDATE = "update";
    public static final String COMMAND_KEYWORD_CHANGE = "change";
    
    //@@author A0138862W
    public static final String COMMAND_ARGUMENTS_REGEX = "(?=(?<index>\\d+))"
                                                        + "(?:(?=.*name to (?:(?<name>.+?)(?:,|$|\\R))?))?"
                                                        + "(?:(?=.*start date to (?:(?<startDate>.+?)(?:,|$|\\R))?))?"
                                                        + "(?:(?=.*end date to (?:(?<endDate>.+?)(?:,|$|\\R))?))?"
                                                        + "(?:(?=.*tags to #(?:(?<tags>.+?)(?:\\s|,\\s|$|,$|\\R))?))?"
                                                        + "(?:(?=.*recur (?<recur>daily|weekly|monthly|yearly)(?:,|$|\\R)))?"
                                                        + ".+";


    public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);

    public static final String COMMAND_FORMAT = "(edit|update|change) <index> [name to <name>,] [start date to <start_date>,] [end date to <end_date>,] [recur (daily|weekly|monthly|yearly),] [tags to #<comma_separated_tags>,]";

    public static final String MESSAGE_USAGE = COMMAND_FORMAT
                                               + "\n"
                                               + "Edits the task identified by the index number used in the last task listing.\n"
                                               + "Example: \n"
                                               + "edit 2 name to parents with dinner, end date to tomorrow 7pm, recur daily, tags to #meal,family";

    public static final String MESSAGE_EDIT_TASK_PROMPT = "Edit the following task: %1$s";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task successfully edited: %1$s";

    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Edit Command] Task reverted: %1$s";
    public static final String MESSAGE_REDO_SUCCESS = "[Redo Edit Command] Edit the following task: %1$s";
    
    public static final String COMMAND_DESCRIPTION = "Editing a task";

    // private MainWindow window;
    private ReadOnlyTask originalTask;
    private Task editedTask;

    private final int targetIndex;
    private Optional<String> name;
    private Optional<String> startDate;
    private Optional<String> endDate;
    private Optional<String> recur;
    private Optional<Set<String>> tags;
    //@@author

    public EditCommand(int targetIndex, Optional<String> name, Optional<String> startDate, Optional<String> endDate, Optional<Set<String>> tags, Optional<String> recur) throws IllegalValueException, ParseException {
        this.targetIndex = targetIndex;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recur = recur;
        this.tags = tags;
    }

    @Override
    public CommandResult execute() {

        try {
            executeEdit();

            model.pushToUndoHistory(this);

            // this is a new command entered by user (not undo/redo)
            // need to clear the redoHistory Stack
            model.clearRedoHistory();
            
            requestHighlightLastActionedRow(editedTask);

            return new CommandResult(COMMAND_KEYWORD_EDIT, String.format(MESSAGE_EDIT_TASK_PROMPT, originalTask));

        } catch (TaskNotFoundException | IndexOutOfBoundsException ie) {
            return new CommandResult(COMMAND_KEYWORD_EDIT, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (IllegalValueException e){
            return new CommandResult(COMMAND_KEYWORD_EDIT, Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        } catch (InvalidEventDateException e){
            return new CommandResult(COMMAND_KEYWORD_EDIT, Messages.MESSAGE_INVALID_DATE);
        }

    }

    @Override
    // @@author A0138862W
    /*
     * Strategy implementation to undo the edit command
     * @see harmony.mastermind.logic.commands.Undoable#undo()
     */
    public CommandResult undo() {

        try {
            model.deleteTask(editedTask);

            // add back the original task
            model.addTask((Task) originalTask);

            model.pushToRedoHistory(this);
            
            requestHighlightLastActionedRow((Task)originalTask);

            return new CommandResult(COMMAND_KEYWORD_EDIT, String.format(MESSAGE_UNDO_SUCCESS, originalTask));
        } catch (UniqueTaskList.TaskNotFoundException pne) {
            return new CommandResult(COMMAND_KEYWORD_EDIT, Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        } catch (DuplicateTaskException e) {
            return new CommandResult(COMMAND_KEYWORD_EDIT, AddCommand.MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    // @@author A0138862W
    /*
     * Strategy implementation to redo the edit command
     * 
     * @see harmony.mastermind.logic.commands.Redoable#redo()
     */
    public CommandResult redo() {

        try {
            executeEdit();

            model.pushToUndoHistory(this);
            
            requestHighlightLastActionedRow(editedTask);

            return new CommandResult(COMMAND_KEYWORD_EDIT, String.format(MESSAGE_REDO_SUCCESS, originalTask));
        } catch (TaskNotFoundException | IndexOutOfBoundsException ie) {
            return new CommandResult(COMMAND_KEYWORD_EDIT, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (IllegalValueException e){
            return new CommandResult(COMMAND_KEYWORD_EDIT, Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        } catch (InvalidEventDateException e){
            return new CommandResult(COMMAND_KEYWORD_EDIT, Messages.MESSAGE_INVALID_DATE);
        }
    }

    // @@author A0138862W
    private void executeEdit() throws TaskNotFoundException, IndexOutOfBoundsException, InvalidEventDateException, IllegalValueException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            throw new IndexOutOfBoundsException();
        }

        if (originalTask == null) {
            originalTask = lastShownList.get(targetIndex - 1);
        }

        // parsing inputs
        // if user provides explicit field and value, we change them
        // otherwise, all omitted field are taken from the original
        String toEditName = name.map(val -> val).orElse(originalTask.getName());
        Date toEditStartDate = startDate.map(val -> prettyTimeParser.parse(val).get(0)).orElse(originalTask.getStartDate());
        Date toEditEndDate = endDate.map(val -> prettyTimeParser.parse(val).get(0)).orElse(originalTask.getEndDate());
        String toEditRecur = recur.map(val -> val).orElse(originalTask.getRecur());
        UniqueTagList toEditTags = new UniqueTagList(tags.map(val -> {
            final Set<Tag> tagSet = new HashSet<>();
            for (String tagName : val) {
                try {
                    tagSet.add(new Tag(tagName));
                } catch (IllegalValueException e) {
                    e.printStackTrace();
                }
            }
            return tagSet;
        }).orElse(originalTask.getTags().toSet()));
        Date toEditCreatedDate = originalTask.getCreatedDate();
        

        // initialize the new task with edited values
        editedTask = buildEditedTask(toEditName, toEditStartDate, toEditEndDate, toEditRecur, toEditTags, toEditCreatedDate);

        model.deleteTask(originalTask);
        model.addTask(editedTask);
    }

    //@@author A0138862W
    /**
     * Attempt to build a task based on edited value. 
     * If this command has build it before, it'll simply return the previous instance.
     * 
     * @param toEditName The edited name
     * @param toEditStartDate the edited start date
     * @param toEditEndDate edited end date
     * @param toEditRecur recurring keyword
     * @param toEditTags tags
     * @param toEditCreatedDate custom creation date
     * @return
     * @throws IllegalValueException if tags are not alphanumeric
     * @throws InvalidEventDateException if start date is after end date
     */
    private Task buildEditedTask(String toEditName, Date toEditStartDate, Date toEditEndDate, String toEditRecur, UniqueTagList toEditTags, Date toEditCreatedDate) throws IllegalValueException, InvalidEventDateException {
        if (editedTask == null) {                    
            TaskBuilder taskBuilder = new TaskBuilder(toEditName);
            taskBuilder.withCreationDate(toEditCreatedDate);
            taskBuilder.withTags(toEditTags);
            taskBuilder.asRecurring(toEditRecur);
            
            if (isEvent(toEditStartDate, toEditEndDate)){
                taskBuilder.asEvent(toEditStartDate,toEditEndDate);
            } else if (isDeadline(toEditStartDate, toEditEndDate)){
                taskBuilder.asDeadline(toEditEndDate);
            } else if (isFloating(toEditStartDate, toEditEndDate)){
                taskBuilder.asFloating();
            }
            editedTask = taskBuilder.build();
        }
        
        return editedTask;
    }
    //@@author
    
    private boolean isFloating(Date toEditStartDate, Date toEditEndDate) {
        return toEditStartDate == null && toEditEndDate == null;
    }

    private boolean isDeadline(Date toEditStartDate, Date toEditEndDate) {
        return toEditStartDate == null && toEditEndDate != null;
    }

    private boolean isEvent(Date toEditStartDate, Date toEditEndDate) {
        return toEditStartDate != null && toEditEndDate != null;
    }
    
}

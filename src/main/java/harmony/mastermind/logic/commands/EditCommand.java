package harmony.mastermind.logic.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
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

    public static final String COMMAND_ARGUMENTS_REGEX = "(?=(?<index>\\d+))"
                                                         + "(?=(?:.*?name\\/\"(?<name>.+?)\")?)"
                                                         + "(?=(?:.*?startDate\\/\"(?<startDate>.+?)\")?)"
                                                         + "(?=(?:.*?endDate\\/\"(?<endDate>.+?)\")?)"
                                                         + "(?=(?:.*tags\\/(?<tags>\\w+(?:,\\w+)*)?)?)"
                                                         + ".*";

    public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);

    public static final String COMMAND_SUMMARY = "Editting a task:"
                                                 + "\n"
                                                 + "("
                                                 + COMMAND_KEYWORD_EDIT
                                                 + " | "
                                                 + COMMAND_KEYWORD_UPDATE
                                                 + ") "
                                                 + "<index> [name/\"<task_name>\"] [startDate/\"<start_date\">] [endDate/\"<end_date\">] [tags/<comma_spearated_tags>]";

    public static final String MESSAGE_USAGE = COMMAND_SUMMARY
                                               + "\n"
                                               + "Edits the task identified by the index number used in the last task listing.\n"
                                               + "Example: "
                                               + COMMAND_KEYWORD_EDIT
                                               + " 1 name/\"I change the task name to this, unspecified field are preserved.";

    public static final String MESSAGE_EDIT_TASK_PROMPT = "Edit the following task: %1$s";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task successfully edited: %1$s";

    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Edit Command] Task reverted: %1$s";
    public static final String MESSAGE_REDO_SUCCESS = "[Redo Edit Command] Edit the following task: %1$s";

    // private MainWindow window;
    private ReadOnlyTask originalTask;
    private Task editedTask;

    private final int targetIndex;
    private Optional<String> name;
    private Optional<String> startDate;
    private Optional<String> endDate;
    private Optional<Set<String>> tags;

    public EditCommand(int targetIndex, Optional<String> name, Optional<String> startDate, Optional<String> endDate, Optional<Set<String>> tags) throws IllegalValueException, ParseException {
        this.targetIndex = targetIndex;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = tags;
    }

    @Override
    public CommandResult execute() {

        try {
            // grabbing the origin task (before edit)
            executeEdit();

            model.pushToUndoHistory(this);

            // this is a new command entered by user (not undo/redo)
            // need to clear the redoHistory Stack
            model.clearRedoHistory();

            return new CommandResult(String.format(MESSAGE_EDIT_TASK_PROMPT, originalTask));

        } catch (TaskNotFoundException | DuplicateTaskException | IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

    }

    @Override
    // @@author A0138862W
    public CommandResult undo() {

        try {
            // remove the task that's previously edited
            model.deleteTask(editedTask);

            // add back the original task
            model.addTask((Task) originalTask);

            model.pushToRedoHistory(this);

            return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, originalTask));
        } catch (UniqueTaskList.TaskNotFoundException pne) {
            return new CommandResult(Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        } catch (DuplicateTaskException e) {
            return new CommandResult(AddCommand.MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    // @@author A0138862W
    public CommandResult redo() {

        try {
            executeEdit();

            model.pushToUndoHistory(this);

            return new CommandResult(String.format(MESSAGE_REDO_SUCCESS, originalTask));
        } catch (TaskNotFoundException | DuplicateTaskException | IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    private void executeEdit() throws TaskNotFoundException, DuplicateTaskException, IndexOutOfBoundsException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            throw new IndexOutOfBoundsException();
        }

        if (originalTask == null) {
            originalTask = lastShownList.get(targetIndex
                                             - 1);
        }

        // if user provides explicit field and value, we change them
        // otherwise, all user omitted field are preserve from the original
        // before edit
        String toEditName = name.map(val -> val).orElse(originalTask.getName());
        Date toEditStartDate = startDate.map(val -> prettyTimeParser.parse(val).get(0)).orElse(originalTask.getStartDate());
        Date toEditEndDate = endDate.map(val -> prettyTimeParser.parse(val).get(0)).orElse(originalTask.getEndDate());
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

        // initialize the new task with edited values
        if (editedTask == null) {
            editedTask = new Task(toEditName, toEditStartDate, toEditEndDate, toEditTags);
        }

        model.deleteTask(originalTask);
        model.addTask(editedTask);
    }

}
